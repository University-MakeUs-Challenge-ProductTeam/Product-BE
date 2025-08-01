package umc.product.domain.chat.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import umc.product.domain.chat.entity.ChatMessage;

import java.util.List;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {
    
    @Query("SELECT cm FROM ChatMessage cm WHERE cm.chatRoom.id = :roomId ORDER BY cm.createdAt ASC")
    List<ChatMessage> findByRoomIdOrderByCreatedAt(@Param("roomId") Long roomId);
    
    @Query("SELECT COUNT(cm) FROM ChatMessage cm WHERE cm.chatRoom.id = :roomId AND cm.senderId != :userId AND cm.isRead = false")
    long countUnreadMessages(@Param("roomId") Long roomId, @Param("userId") Long userId);
} 