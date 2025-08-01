package umc.product.domain.chat.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import umc.product.domain.chat.entity.ChatMessage;
import umc.product.domain.chat.entity.enums.MessageType;

import java.time.LocalDateTime;

@Schema(description = "채팅 메시지 응답 DTO")
@Builder
public record ChatMessageResponse(
    @Schema(description = "메시지 ID")
    Long messageId,
    
    @Schema(description = "채팅방 ID")
    Long roomId,
    
    @Schema(description = "발신자 ID")
    Long senderId,
    
    @Schema(description = "메시지 내용")
    String content,
    
    @Schema(description = "메시지 타입")
    MessageType messageType,
    
    @Schema(description = "읽음 여부")
    Boolean isRead,
    
    @Schema(description = "생성 시간")
    LocalDateTime createdAt
) {
    public static ChatMessageResponse from(ChatMessage message) {
        return ChatMessageResponse.builder()
                .messageId(message.getId())
                .roomId(message.getChatRoom().getId())
                .senderId(message.getSenderId())
                .content(message.getContent())
                .messageType(message.getMessageType())
                .isRead(message.getIsRead() != null ? message.getIsRead() : false)
                .createdAt(message.getCreatedAt())
                .build();
    }
} 