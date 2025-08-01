package umc.product.domain.chat.service;

import umc.product.domain.chat.dto.response.ChatMessageResponse;
import umc.product.domain.chat.dto.response.ChatRoomResponse;
import umc.product.domain.chat.dto.response.list.ChatRoomListResponse;
import umc.product.domain.chat.entity.ChatMessage;
import umc.product.domain.chat.entity.ChatRoom;

import java.util.List;

public interface ChatService {
    ChatRoom getOrCreateChatRoom(Long user1Id, Long user2Id);
    ChatMessage sendMessage(Long senderId, Long receiverId, String content);
    ChatRoomListResponse getUserChatRooms(Long userId);
    List<ChatMessageResponse> getRoomMessages(Long roomId, Long userId);
    void markMessagesAsRead(Long roomId, Long userId);
} 