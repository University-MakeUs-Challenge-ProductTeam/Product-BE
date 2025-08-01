package umc.product.domain.chat.serviceImpl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import umc.product.domain.chat.converter.response.ChatConverter;
import umc.product.domain.chat.dto.response.ChatMessageResponse;
import umc.product.domain.chat.dto.response.ChatRoomResponse;
import umc.product.domain.chat.dto.response.list.ChatRoomListResponse;
import umc.product.domain.chat.entity.ChatMessage;
import umc.product.domain.chat.entity.ChatRoom;
import umc.product.domain.chat.entity.enums.MessageType;
import umc.product.domain.chat.repository.ChatMessageRepository;
import umc.product.domain.chat.repository.ChatRoomRepository;
import umc.product.domain.chat.service.ChatService;
import umc.product.domain.chat.status.ChatErrorStatus;
import umc.product.domain.member.entity.Member;
import umc.product.domain.member.repository.jpa.MemberJpaRepository;
import umc.product.global.common.exception.RestApiException;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ChatServiceImpl implements ChatService {
    
    private final ChatRoomRepository chatRoomRepository;
    private final ChatMessageRepository chatMessageRepository;
    private final ChatConverter chatConverter;
    private final MemberJpaRepository memberJpaRepository;
    private final KafkaTemplate<String, ChatMessageResponse> kafkaTemplate;
    
    @Override
    @Transactional
    public ChatRoom getOrCreateChatRoom(Long user1Id, Long user2Id) {
        return chatRoomRepository.findByUserIds(user1Id, user2Id)
                .orElseGet(() -> {
                    ChatRoom newRoom = new ChatRoom(user1Id, user2Id);
                    return chatRoomRepository.save(newRoom);
                });
    }
    
    @Override
    @Transactional
    public ChatMessage sendMessage(Long senderId, Long receiverId, String content) {
        // 채팅방 조회 또는 생성
        ChatRoom chatRoom = getOrCreateChatRoom(senderId, receiverId);
        
        // 메시지 저장
        ChatMessage message = ChatMessage.builder()
                .chatRoom(chatRoom)
                .senderId(senderId)
                .content(content)
                .messageType(MessageType.TEXT)
                .build();
        
        ChatMessage savedMessage = chatMessageRepository.save(message);
        
        // Kafka에 메시지 발행 (순서 보장)
        publishMessageToKafka(chatRoom.getId(), ChatMessageResponse.from(savedMessage));
        
        log.info("Message sent: roomId={}, senderId={}, content={}", 
                chatRoom.getId(), senderId, content);
        
        return savedMessage;
    }
    
    private void publishMessageToKafka(Long roomId, ChatMessageResponse message) {
        try {
            // Kafka에 메시지 발행 (채팅방 ID를 키로 사용하여 같은 파티션으로 전송)
            kafkaTemplate.send("chat-messages", roomId.toString(), message);
            log.info("Message published to Kafka: roomId={}, messageId={}", 
                    roomId, message.messageId());
        } catch (Exception e) {
            log.error("Failed to publish message to Kafka: roomId={}, error={}", 
                    roomId, e.getMessage());
        }
    }
    
    @Override
    public ChatRoomListResponse getUserChatRooms(Long userId) {
        List<ChatRoom> rooms = chatRoomRepository.findByUserId(userId);
        
        List<ChatRoomResponse> roomResponses = rooms.stream()
                .map(room -> {
                    Long otherUserId = room.getOtherUserId(userId);
                    Member otherUser = memberJpaRepository.findById(otherUserId)
                            .orElse(null); // 사용자가 존재하지 않으면 null 반환
                    
                    // 마지막 메시지 조회
                    List<ChatMessage> messages = chatMessageRepository
                            .findByRoomIdOrderByCreatedAt(room.getId());
                    ChatMessage lastMessage = messages.isEmpty() ? null : messages.get(messages.size() - 1);
                    
                    // 안읽은 메시지 수
                    long unreadCount = chatMessageRepository.countUnreadMessages(room.getId(), userId);
                    
                    return ChatRoomResponse.builder()
                            .roomId(room.getId())
                            .otherUserId(otherUserId)
                            .otherUserName(otherUser != null ? otherUser.getName() : "알 수 없는 사용자")
                            .lastMessage(lastMessage != null ? lastMessage.getContent() : null)
                            .lastMessageTime(lastMessage != null ? lastMessage.getCreatedAt() : null)
                            .unreadCount(unreadCount)
                            .build();
                })
                .collect(Collectors.toList());
        
        return ChatRoomListResponse.builder()
                .chatRoomResponseList(roomResponses)
                .build();
    }
    
    @Override
    public List<ChatMessageResponse> getRoomMessages(Long roomId, Long userId) {
        ChatRoom chatRoom = chatRoomRepository.findById(roomId)
                .orElseThrow(() -> new RestApiException(ChatErrorStatus.CHAT_ROOM_NOT_FOUND));
        
        if (!chatRoom.isParticipant(userId)) {
            throw new RestApiException(ChatErrorStatus.ACCESS_DENIED);
        }
        
        return chatMessageRepository.findByRoomIdOrderByCreatedAt(roomId)
                .stream()
                .map(ChatMessageResponse::from)
                .collect(Collectors.toList());
    }
    
    @Override
    @Transactional
    public void markMessagesAsRead(Long roomId, Long userId) {
        ChatRoom chatRoom = chatRoomRepository.findById(roomId)
                .orElseThrow(() -> new RestApiException(ChatErrorStatus.CHAT_ROOM_NOT_FOUND));
        
        if (!chatRoom.isParticipant(userId)) {
            throw new RestApiException(ChatErrorStatus.ACCESS_DENIED);
        }
        
        List<ChatMessage> unreadMessages = chatMessageRepository
                .findByRoomIdOrderByCreatedAt(roomId)
                .stream()
                .filter(message -> !message.getSenderId().equals(userId) && !message.getIsRead())
                .collect(Collectors.toList());
        
        unreadMessages.forEach(ChatMessage::markAsRead);
        chatMessageRepository.saveAll(unreadMessages);
        
        log.info("Messages marked as read: roomId={}, userId={}, count={}", 
                roomId, userId, unreadMessages.size());
    }
} 