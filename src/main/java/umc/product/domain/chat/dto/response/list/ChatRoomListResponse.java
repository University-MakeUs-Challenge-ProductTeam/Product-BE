package umc.product.domain.chat.dto.response.list;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import umc.product.domain.chat.dto.response.ChatRoomResponse;

import java.util.List;

@Schema(description = "채팅방 목록 조회 응답 DTO")
@Getter
@Builder
@AllArgsConstructor
public class ChatRoomListResponse {
    
    @Schema(description = "채팅방 목록")
    private final List<ChatRoomResponse> chatRoomResponseList;
} 