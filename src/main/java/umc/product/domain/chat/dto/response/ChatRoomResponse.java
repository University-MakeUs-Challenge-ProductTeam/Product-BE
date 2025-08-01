package umc.product.domain.chat.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.time.LocalDateTime;

@Schema(description = "채팅방 응답 DTO")
@Builder
public record ChatRoomResponse(
    @Schema(description = "채팅방 ID")
    Long roomId,
    
    @Schema(description = "상대방 사용자 ID")
    Long otherUserId,
    
    @Schema(description = "상대방 사용자 이름")
    String otherUserName,
    
    @Schema(description = "마지막 메시지")
    String lastMessage,
    
    @Schema(description = "마지막 메시지 시간")
    LocalDateTime lastMessageTime,
    
    @Schema(description = "안읽은 메시지 수")
    Long unreadCount
) {
} 