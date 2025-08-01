package umc.product.domain.chat.converter.response;

import org.springframework.stereotype.Component;
import umc.product.domain.chat.dto.response.ChatMessageResponse;
import umc.product.domain.chat.dto.response.ChatRoomResponse;
import umc.product.domain.chat.dto.response.list.ChatRoomListResponse;
import umc.product.domain.chat.entity.ChatMessage;
import umc.product.domain.chat.entity.ChatRoom;

import java.util.List;

@Component
public class ChatConverter {
    
    public ChatMessageResponse toChatMessageResponse(ChatMessage message) {
        return ChatMessageResponse.from(message);
    }
    
    public ChatRoomResponse toChatRoomResponse(ChatRoom room, Long currentUserId, String otherUserName, 
                                             String lastMessage, Long unreadCount) {
        return ChatRoomResponse.builder()
                .roomId(room.getId())
                .otherUserId(room.getOtherUserId(currentUserId))
                .otherUserName(otherUserName)
                .lastMessage(lastMessage)
                .lastMessageTime(lastMessage != null ? room.getUpdatedAt() : null)
                .unreadCount(unreadCount)
                .build();
    }
    
    public ChatRoomListResponse toChatRoomListResponse(List<ChatRoomResponse> chatRoomResponseList) {
        return ChatRoomListResponse.builder()
                .chatRoomResponseList(chatRoomResponseList)
                .build();
    }
} 