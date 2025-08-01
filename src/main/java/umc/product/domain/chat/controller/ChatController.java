package umc.product.domain.chat.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import umc.product.domain.chat.adviser.ChatAdviser;
import umc.product.domain.chat.dto.request.ChatMessageRequest;
import umc.product.domain.chat.dto.response.ChatMessageResponse;
import umc.product.domain.chat.dto.response.list.ChatRoomListResponse;
import umc.product.domain.member.entity.Member;
import umc.product.global.common.base.BaseResponse;
import umc.product.global.config.security.auth.CurrentMember;

import java.util.List;

@Tag(name = "채팅 API", description = "채팅 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/chats")
public class ChatController {
    
    private final ChatAdviser chatAdviser;
    
    @Operation(summary = "채팅방 목록 조회 API", description = "사용자의 채팅방 목록을 조회하는 API입니다.")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "채팅방 목록 조회 성공"
            )
    })
    @GetMapping("/rooms")
    public BaseResponse<ChatRoomListResponse> getChatRooms(@CurrentMember Member member) {
        return BaseResponse.onSuccess(chatAdviser.getUserChatRooms(member));
    }
    
    @Operation(summary = "채팅방 메시지 조회 API", description = "특정 채팅방의 메시지를 조회하는 API입니다.")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "채팅방 메시지 조회 성공"
            )
    })
    @Parameters({
            @Parameter(name = "roomId", description = "채팅방 ID"),
    })
    @GetMapping("/rooms/{roomId}/messages")
    public BaseResponse<List<ChatMessageResponse>> getRoomMessages(
            @PathVariable Long roomId,
            @CurrentMember Member member) {
        return BaseResponse.onSuccess(chatAdviser.getRoomMessages(roomId, member));
    }
    
    @Operation(summary = "메시지 전송 API", description = "메시지를 전송하는 API입니다.")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "메시지 전송 성공"
            )
    })
    @PostMapping("/send")
    public BaseResponse<ChatMessageResponse> sendMessage(
            @RequestBody ChatMessageRequest request,
            @CurrentMember Member member) {
        return BaseResponse.onSuccess(
                chatAdviser.sendMessage(member, request.receiverId(), request.content())
        );
    }
    
    @Operation(summary = "메시지 읽음 처리 API", description = "채팅방의 메시지를 읽음 처리하는 API입니다.")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "메시지 읽음 처리 성공"
            )
    })
    @Parameters({
            @Parameter(name = "roomId", description = "채팅방 ID"),
    })
    @PostMapping("/rooms/{roomId}/read")
    public BaseResponse<Void> markMessagesAsRead(
            @PathVariable Long roomId,
            @CurrentMember Member member) {
        chatAdviser.markMessagesAsRead(roomId, member);
        return BaseResponse.onSuccess(null);
    }
} 