package umc.product.domain.suggestion.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import umc.product.domain.member.entity.Member;
import umc.product.domain.suggestion.adviser.SuggestionCommentAdviser;
import umc.product.domain.suggestion.dto.request.SuggestionCommentRequest;
import umc.product.domain.suggestion.dto.response.SuggestionCommentIdResponse;
import umc.product.global.common.base.BaseResponse;
import umc.product.global.config.security.auth.CurrentMember;

@Tag(name = "건의함 댓글 API", description = "건의함 댓글 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/suggestion")
public class SuggestionCommentController {
    private final SuggestionCommentAdviser suggestionCommentAdviser;
    @Operation(summary = "건의함 댓글 작성 API", description = "건의함 댓글을 작성하는 API입니다")
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

    @Operation(summary = "건의함 댓글 수정 API", description = "건의함 댓글을 수정하는 API입니다")
    @ApiResponses( value = {
            @ApiResponse(responseCode = "COMMON200", description = "성공"),
            @ApiResponse(responseCode = "UNIVERSITY001", description = "대학교명을 잘못 입력하였을 경우 발생"),
            @ApiResponse(responseCode = "BRANCH001", description = "대학교가 지부랑 연결되어 있지 않을 경우 발생")
    })
    @PatchMapping("/comment")
    public BaseResponse<SuggestionCommentIdResponse> patchSuggestionComment(@CurrentMember Member member,
                                                                            @RequestParam Long suggestionCommentId,
                                                                            @RequestBody SuggestionCommentRequest suggestionCommentRequest
    ) {
        return BaseResponse.onSuccess(suggestionCommentAdviser.patchSuggestionComment(member, suggestionCommentId, suggestionCommentRequest));
    }

    @Operation(summary = "건의함 댓글 삭제 API", description = "건의함 댓글을 삭제하는 API입니다")
    @ApiResponses( value = {
            @ApiResponse(responseCode = "COMMON200", description = "성공"),
            @ApiResponse(responseCode = "UNIVERSITY001", description = "대학교명을 잘못 입력하였을 경우 발생"),
            @ApiResponse(responseCode = "BRANCH001", description = "대학교가 지부랑 연결되어 있지 않을 경우 발생")
    })
    @DeleteMapping("/comment")
    public BaseResponse<SuggestionCommentIdResponse> deleteSuggestionComment(@CurrentMember Member member,
                                                                             @RequestParam Long suggestionCommentId

    ) {
        return BaseResponse.onSuccess(suggestionCommentAdviser.deleteSuggestionComment(member, suggestionCommentId));
    }
}
