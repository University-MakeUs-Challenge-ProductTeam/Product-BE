package umc.product.domain.chat.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import umc.product.domain.chat.entity.ChatRoom;

import java.util.List;
import java.util.Optional;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {
    
    @Query("SELECT cr FROM ChatRoom cr WHERE cr.user1Id = :userId OR cr.user2Id = :userId")
    List<ChatRoom> findByUserId(@Param("userId") Long userId);
    
    @Query("SELECT cr FROM ChatRoom cr WHERE " +
           "(cr.user1Id = :user1Id AND cr.user2Id = :user2Id) OR " +
           "(cr.user1Id = :user2Id AND cr.user2Id = :user1Id)")
    Optional<ChatRoom> findByUserIds(@Param("user1Id") Long user1Id, @Param("user2Id") Long user2Id);
} 