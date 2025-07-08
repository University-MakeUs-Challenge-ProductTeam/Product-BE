package umc.product.domain.notice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import umc.product.domain.member.entity.Member;
import umc.product.domain.notice.adviser.NoticeAdviser;
import umc.product.domain.notice.dto.response.NoticeCheckResponse;
import umc.product.domain.notice.dto.response.NoticeDetailResponse;
import umc.product.domain.notice.dto.response.list.NoticeListResponse;
import umc.product.domain.notice.entity.enums.NoticeTarget;
import umc.product.global.common.base.BaseResponse;
import umc.product.global.config.security.auth.CurrentMember;

import java.util.List;

@Tag(name = "공지 API", description = "공지 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/notices")
public class NoticeController {
    private final NoticeAdviser noticeAdviser;

    @Operation(summary = "공지 목록 조회 API", description = "대상별 공지 목록을 최신순으로 조회하는 API입니다. target 파라미터가 없으면 모든 공지를 조회합니다.")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "공지 목록 조회 성공"
            )
    })
    @Parameters({
            @Parameter(name = "target", description = "공지 대상 (CENTRAL, BRANCH, UNIVERSITY). 생략하면 모든 공지를 조회합니다."),
    })
    @GetMapping
    public BaseResponse<NoticeListResponse> getNoticeList(
            @RequestParam(required = false) NoticeTarget target,
            @CurrentMember Member member) {
        return BaseResponse.onSuccess(noticeAdviser.getNoticeList(member, target));
    }

    @Operation(summary = "공지 상세 조회 API", description = "공지 상세 정보를 조회하는 API입니다.")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "공지 상세 조회 성공"
            )
    })
    @Parameters({
            @Parameter(name = "noticeId", description = "공지 ID"),
    })
    @GetMapping("/{noticeId}")
    public BaseResponse<NoticeDetailResponse> getNoticeDetail(
            @PathVariable Long noticeId,
            @CurrentMember Member member) {
        return BaseResponse.onSuccess(noticeAdviser.getNoticeDetail(member, noticeId));
    }

    @Operation(summary = "공지 열람 체크 API", description = "공지 열람 상태를 체크하는 API입니다.")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "공지 열람 체크 성공"
            )
    })
    @Parameters({
            @Parameter(name = "noticeId", description = "공지 ID"),
    })
    @PostMapping("/{noticeId}/check")
    public BaseResponse<NoticeCheckResponse> checkNotice(
            @PathVariable Long noticeId,
            @CurrentMember Member member) {
        return BaseResponse.onSuccess(noticeAdviser.checkNotice(member, noticeId));
    }
}
