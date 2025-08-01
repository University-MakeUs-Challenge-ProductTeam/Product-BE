package umc.product.domain.chat.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLRestriction;
import umc.product.domain.chat.entity.enums.MessageType;
import umc.product.global.common.base.BaseEntity;

@Entity
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SQLRestriction("deleted_at is null")
@Table(name = "chat_messages")
public class ChatMessage extends BaseEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "chat_message_id")
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chat_room_id", nullable = false)
    private ChatRoom chatRoom;
    
    @Column(name = "sender_id", nullable = false)
    private Long senderId;
    
    @Column(name = "content", nullable = false, columnDefinition = "TEXT")
    private String content;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "message_type")
    private MessageType messageType = MessageType.TEXT;
    
    @Column(name = "is_read")
    private Boolean isRead = false;
    
    public Boolean getIsRead() {
        return isRead != null ? isRead : false;
    }
    
    @Builder
    public ChatMessage(ChatRoom chatRoom, Long senderId, String content, MessageType messageType) {
        this.chatRoom = chatRoom;
        this.senderId = senderId;
        this.content = content;
        this.messageType = messageType;
        this.isRead = false; // 명시적으로 초기화
    }
    
    public void markAsRead() {
        this.isRead = true;
    }
} 