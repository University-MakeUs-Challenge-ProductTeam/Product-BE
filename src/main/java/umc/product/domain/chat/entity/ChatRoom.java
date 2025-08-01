package umc.product.domain.chat.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLRestriction;
import umc.product.global.common.base.BaseEntity;

import java.util.ArrayList;
import java.util.List;

@Entity
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SQLRestriction("deleted_at is null")
@Table(name = "chat_rooms")
public class ChatRoom extends BaseEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "chat_room_id")
    private Long id;
    
    @Column(name = "user1_id", nullable = false)
    private Long user1Id;
    
    @Column(name = "user2_id", nullable = false)
    private Long user2Id;
    
    @Builder.Default
    @OneToMany(mappedBy = "chatRoom", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ChatMessage> messages = new ArrayList<>();
    
    public ChatRoom(Long user1Id, Long user2Id) {
        this.user1Id = Math.min(user1Id, user2Id);
        this.user2Id = Math.max(user1Id, user2Id);
    }
    
    public boolean isParticipant(Long userId) {
        return user1Id.equals(userId) || user2Id.equals(userId);
    }
    
    public Long getOtherUserId(Long userId) {
        if (user1Id.equals(userId)) {
            return user2Id;
        } else if (user2Id.equals(userId)) {
            return user1Id;
        }
        throw new IllegalArgumentException("User is not participant of this room");
    }
} 