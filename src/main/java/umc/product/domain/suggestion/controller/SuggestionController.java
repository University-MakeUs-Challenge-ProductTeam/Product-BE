package umc.product.domain.suggestion.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;
import umc.product.domain.member.entity.Member;
import umc.product.domain.suggestion.adviser.SuggestionAdviser;
import umc.product.domain.suggestion.dto.request.SuggestionRequest;
import umc.product.domain.suggestion.dto.response.SuggestionDetailResponse;
import umc.product.domain.suggestion.dto.response.SuggestionGetResponse;
import umc.product.domain.suggestion.dto.response.SuggestionIdResponse;
import umc.product.global.common.base.BaseResponse;
import umc.product.global.config.security.auth.CurrentMember;

@Tag(name = "건의함 API", description = "건의함 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/suggestion")
public class SuggestionController {
    private final SuggestionAdviser suggestionAdviser;
    @Operation(summary = "건의함 작성 API", description = "건의함을 작성하는 API입니다")
    @ApiResponses( value = {
            @ApiResponse(responseCode = "COMMON200", description = "성공"),
            @ApiResponse(responseCode = "UNIVERSITY001", description = "대학교명을 잘못 입력하였을 경우 발생"),
            @ApiResponse(responseCode = "BRANCH001", description = "대학교가 지부랑 연결되어 있지 않을 경우 발생")
    })
    @PostMapping("/")
    public BaseResponse<SuggestionIdResponse> postSuggestion(@CurrentMember Member member,
                                                             @RequestBody SuggestionRequest suggestionRequest
            ) {
        return BaseResponse.onSuccess(suggestionAdviser.postSuggestion(member, suggestionRequest));
    }

    @Operation(summary = "건의함 수정 API", description = "건의함을 수정하는 API입니다")
    @ApiResponses( value = {
            @ApiResponse(responseCode = "COMMON200", description = "성공"),
            @ApiResponse(responseCode = "UNIVERSITY001", description = "대학교명을 잘못 입력하였을 경우 발생"),
            @ApiResponse(responseCode = "BRANCH001", description = "대학교가 지부랑 연결되어 있지 않을 경우 발생")
    })
    @PatchMapping("/")
    public BaseResponse<SuggestionIdResponse> patchSuggestion(@CurrentMember Member member,
                                                              @RequestParam Long suggestionId,
                                                              @RequestBody SuggestionRequest suggestionRequest
    ) {
        return BaseResponse.onSuccess(suggestionAdviser.patchSuggestion(member, suggestionId, suggestionRequest));
    }

    @Operation(summary = "건의함 수정 API", description = "건의함을 수정하는 API입니다")
    @ApiResponses( value = {
            @ApiResponse(responseCode = "COMMON200", description = "성공"),
            @ApiResponse(responseCode = "UNIVERSITY001", description = "대학교명을 잘못 입력하였을 경우 발생"),
            @ApiResponse(responseCode = "BRANCH001", description = "대학교가 지부랑 연결되어 있지 않을 경우 발생")
    })
    @DeleteMapping("/")
    public BaseResponse<SuggestionIdResponse> deleteSuggestion(@CurrentMember Member member,
                                                               @RequestParam Long suggestionId
    ) {
        return BaseResponse.onSuccess(suggestionAdviser.deleteSuggestion(member, suggestionId));
    }

    @Operation(summary = "건의함 불러오기 API", description = "건의함을 불러오는 API입니다")
    @ApiResponses( value = {
            @ApiResponse(responseCode = "COMMON200", description = "성공"),
            @ApiResponse(responseCode = "UNIVERSITY001", description = "대학교명을 잘못 입력하였을 경우 발생"),
            @ApiResponse(responseCode = "BRANCH001", description = "대학교가 지부랑 연결되어 있지 않을 경우 발생")
    })
    @GetMapping("/")
    public BaseResponse<SuggestionGetResponse> getSuggestion(@CurrentMember Member member,
                                                             @RequestParam Integer page,
                                                             @RequestParam Integer size
    ) {
        return BaseResponse.onSuccess(suggestionAdviser.getSuggestion(member, PageRequest.of(page, size)));
    }

    @Operation(summary = "건의함 세부사항 불러오기 API", description = "건의함의 세부사항 불러오는 API입니다")
    @ApiResponses( value = {
            @ApiResponse(responseCode = "COMMON200", description = "성공"),
            @ApiResponse(responseCode = "UNIVERSITY001", description = "대학교명을 잘못 입력하였을 경우 발생"),
            @ApiResponse(responseCode = "BRANCH001", description = "대학교가 지부랑 연결되어 있지 않을 경우 발생")
    })
    @GetMapping("/detail")
    public BaseResponse<SuggestionDetailResponse> getSuggestionDetail(@CurrentMember Member member,
                                                                      @RequestParam Long suggestionId
    ) {
        return BaseResponse.onSuccess(suggestionAdviser.getSuggestionDetail(member, suggestionId));
    }

    @Operation(summary = "건의함 답장 여부 API", description = "건의함 답장 여부를 수정하는 API입니다")
    @ApiResponses( value = {
            @ApiResponse(responseCode = "COMMON200", description = "성공"),
            @ApiResponse(responseCode = "UNIVERSITY001", description = "대학교명을 잘못 입력하였을 경우 발생"),
            @ApiResponse(responseCode = "BRANCH001", description = "대학교가 지부랑 연결되어 있지 않을 경우 발생")
    })
    @PatchMapping("/status")
    public BaseResponse<SuggestionIdResponse> patchSuggestionStatus(@CurrentMember Member member,
                                                                    @RequestParam Long suggestionId,
                                                                    @RequestParam boolean completedStatus
    ) {
        return BaseResponse.onSuccess(suggestionAdviser.patchSuggestionStatus(member, suggestionId, completedStatus));
    }
}
