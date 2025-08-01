package umc.product.domain.chat.consumer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;
import umc.product.domain.chat.dto.response.ChatMessageResponse;
import umc.product.domain.chat.repository.ChatRoomRepository;

@Slf4j
@Component
@RequiredArgsConstructor
public class ChatMessageConsumer {
    
    private final SimpMessagingTemplate messagingTemplate;
    private final ChatRoomRepository chatRoomRepository;
    
    @KafkaListener(topics = "chat-messages", groupId = "chat-group")
    public void handleChatMessage(ChatMessageResponse message) {
        log.info("Received message from Kafka: {}", message);
        
        try {
            // WebSocket을 통해 실시간으로 메시지 전송
            sendMessageToUsers(message);
        } catch (Exception e) {
            log.error("Failed to send message via WebSocket: {}", e.getMessage());
        }
    }
    
    private void sendMessageToUsers(ChatMessageResponse message) {
        // 채팅방의 두 사용자에게 메시지 전송
        String roomId = message.roomId().toString();
        
        // ChatRoom에서 receiverId 구하기
        Long receiverId = chatRoomRepository.findById(message.roomId())
                .map(room -> room.getOtherUserId(message.senderId()))
                .orElse(null);
        
        if (receiverId != null) {
            // 사용자별 개인 채널로 메시지 전송
            messagingTemplate.convertAndSend("/topic/chat/" + message.senderId(), message);
            messagingTemplate.convertAndSend("/topic/chat/" + receiverId, message);
            
            // 채팅방 채널로도 전송 (필요시)
            messagingTemplate.convertAndSend("/topic/chat/room/" + roomId, message);
            
            log.info("Message sent via WebSocket: roomId={}, senderId={}, receiverId={}", 
                    roomId, message.senderId(), receiverId);
        } else {
            log.warn("Could not find receiver for message: roomId={}, senderId={}", 
                    message.roomId(), message.senderId());
        }
    }
} 