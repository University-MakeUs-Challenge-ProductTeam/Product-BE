package umc.product.domain.chat.adviser;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import umc.product.domain.chat.converter.response.ChatConverter;
import umc.product.domain.chat.dto.response.ChatMessageResponse;
import umc.product.domain.chat.dto.response.ChatRoomResponse;
import umc.product.domain.chat.dto.response.list.ChatRoomListResponse;
import umc.product.domain.chat.entity.ChatMessage;
import umc.product.domain.chat.entity.ChatRoom;
import umc.product.domain.chat.service.ChatService;
import umc.product.domain.member.entity.Member;
import umc.product.domain.member.repository.jpa.MemberJpaRepository;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ChatAdviser {
    
    private final ChatService chatService;
    private final ChatConverter chatConverter;
    private final MemberJpaRepository memberJpaRepository;
    
    public ChatRoomListResponse getUserChatRooms(Member member) {
        return chatService.getUserChatRooms(member.getId());
    }
    
    public List<ChatMessageResponse> getRoomMessages(Long roomId, Member member) {
        return chatService.getRoomMessages(roomId, member.getId());
    }
    
    public ChatMessageResponse sendMessage(Member member, Long receiverId, String content) {
        ChatMessage message = chatService.sendMessage(member.getId(), receiverId, content);
        return ChatMessageResponse.from(message);
    }
    
    public void markMessagesAsRead(Long roomId, Member member) {
        chatService.markMessagesAsRead(roomId, member.getId());
    }
} 