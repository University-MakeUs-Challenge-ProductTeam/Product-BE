package umc.product.domain.suggestion.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;
import umc.product.domain.member.entity.Member;
import umc.product.domain.suggestion.adviser.SuggestionAdviser;
import umc.product.domain.suggestion.adviser.SuggestionCommentAdviser;
import umc.product.domain.suggestion.dto.request.SuggestionCommentRequest;
import umc.product.domain.suggestion.dto.request.SuggestionRequest;
import umc.product.domain.suggestion.dto.response.*;
import umc.product.global.common.base.BaseResponse;
import umc.product.global.config.security.auth.CurrentMember;

@Tag(name = "어드민 건의함 API", description = "어드민 건의함 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/web/admin/suggestion")
public class AdminSuggestionController {
    private final SuggestionAdviser suggestionAdviser;
    private final SuggestionCommentAdviser suggestionCommentAdviser;
    @Operation(summary = "건의함 불러오기 API", description = "건의함을 불러오는 API입니다. ADMIN 전용")
    @ApiResponses( value = {
            @ApiResponse(responseCode = "COMMON200", description = "성공"),
            @ApiResponse(responseCode = "UNIVERSITY001", description = "대학교명을 잘못 입력하였을 경우 발생"),
            @ApiResponse(responseCode = "BRANCH001", description = "대학교가 지부랑 연결되어 있지 않을 경우 발생")
    })
    @GetMapping("/list")
    public BaseResponse<SuggestionGetResponse> getSuggestion(@CurrentMember Member member,
                                                             @RequestParam Integer page,
                                                             @RequestParam Integer size
    ) {
        return BaseResponse.onSuccess(suggestionAdviser.getSuggestion(member, PageRequest.of(page, size)));
    }

    @Operation(summary = "건의함 댓글 작성 API", description = "건의함 댓글을 작성하는 API입니다. ADMIN 전용")
    @ApiResponses( value = {
            @ApiResponse(responseCode = "COMMON200", description = "성공"),
            @ApiResponse(responseCode = "UNIVERSITY001", description = "대학교명을 잘못 입력하였을 경우 발생"),
            @ApiResponse(responseCode = "BRANCH001", description = "대학교가 지부랑 연결되어 있지 않을 경우 발생")
    })
    @PostMapping("/comment")
    public BaseResponse<SuggestionCommentIdResponse> postSuggestionComment(@CurrentMember Member member,
                                                                           @RequestParam Long suggestionId,
                                                                           @RequestParam(required = false) Long suggestionCommentId,
                                                                           @Valid @RequestBody SuggestionCommentRequest suggestionCommentRequest
    ) {
        return BaseResponse.onSuccess(suggestionCommentAdviser.postSuggestionComment(member, suggestionId, suggestionCommentId, suggestionCommentRequest));
    }

    @Operation(summary = "건의함 댓글 수정 API", description = "건의함 댓글을 수정하는 API입니다. ADMIN 전용")
    @ApiResponses( value = {
            @ApiResponse(responseCode = "COMMON200", description = "성공"),
            @ApiResponse(responseCode = "UNIVERSITY001", description = "대학교명을 잘못 입력하였을 경우 발생"),
            @ApiResponse(responseCode = "BRANCH001", description = "대학교가 지부랑 연결되어 있지 않을 경우 발생")
    })
    @PatchMapping("/comment/{commentId}")
    public BaseResponse<SuggestionCommentIdResponse> patchSuggestionComment(@CurrentMember Member member,
                                                                            @PathVariable(name = "commentId") Long commentId,
                                                                            @RequestBody SuggestionCommentRequest suggestionCommentRequest
    ) {
        return BaseResponse.onSuccess(suggestionCommentAdviser.patchSuggestionComment(member, commentId, suggestionCommentRequest));
    }

    @Operation(summary = "건의함 댓글 삭제 API", description = "건의함 댓글을 삭제하는 API입니다. ADMIN 전용")
    @ApiResponses( value = {
            @ApiResponse(responseCode = "COMMON200", description = "성공"),
            @ApiResponse(responseCode = "UNIVERSITY001", description = "대학교명을 잘못 입력하였을 경우 발생"),
            @ApiResponse(responseCode = "BRANCH001", description = "대학교가 지부랑 연결되어 있지 않을 경우 발생")
    })
    @DeleteMapping("/comment/{commentId}")
    public BaseResponse<SuggestionCommentIdResponse> deleteSuggestionComment(@CurrentMember Member member,
                                                                             @PathVariable(name = "commentId") Long commentId

    ) {
        return BaseResponse.onSuccess(suggestionCommentAdviser.deleteSuggestionComment(member, commentId));
    }
}
