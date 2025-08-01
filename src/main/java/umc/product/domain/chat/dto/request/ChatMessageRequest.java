package umc.product.domain.chat.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import umc.product.domain.chat.entity.enums.MessageType;

@Schema(description = "채팅 메시지 전송 요청 DTO")
@Builder
public record ChatMessageRequest(
    @Schema(description = "수신자 ID")
    Long receiverId,
    
    @Schema(description = "메시지 내용")
    String content,
    
    @Schema(description = "메시지 타입")
    MessageType messageType
) {
} 