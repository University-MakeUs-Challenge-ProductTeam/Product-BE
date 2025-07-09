package umc.product.domain.notice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import umc.product.domain.member.entity.Member;
import umc.product.domain.notice.adviser.AdminNoticeAdviser;
import umc.product.domain.notice.dto.request.admin.AdminNoticeListRequest;
import umc.product.domain.notice.dto.response.admin.AdminNoticeDetailResponse;
import umc.product.domain.notice.dto.response.admin.AdminNoticeResponse;
import umc.product.domain.notice.dto.response.admin.list.AdminNoticeCheckStatusListResponse;
import umc.product.global.common.base.BaseResponse;
import umc.product.global.config.security.auth.CurrentMember;

@Tag(name = "[운영진용] 공지 API", description = "[운영진용] 공지 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/web/admin/notices")
public class AdminNoticeController {
    private final AdminNoticeAdviser adminNoticeAdviser;

    @Operation(summary = "[운영진용] 공지 목록 조회 API", description = "[운영진용] 공지 목록을 최신순으로 조회하는 API입니다. 파라미터가 없으면 모든 공지를 조회합니다.")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "공지 목록 조회 성공"
            )
    })
    @Parameters({
            @Parameter(name = "target", description = "생략하면 모든 공지를 조회합니다."),
    })
    @GetMapping
    public BaseResponse<Page<AdminNoticeResponse>> getNoticeList(
            // 쿼리 파라미터로 필터 조건, 검색 키워드 받음
            @ModelAttribute AdminNoticeListRequest request,
            Pageable pageable,
            @CurrentMember Member member) {
        return BaseResponse.onSuccess(adminNoticeAdviser.getAdminNoticeList(request, pageable));
    }

    @Operation(summary = "[운영진용] 공지 상세 조회 API", description = "[운영진용] 공지 상세를 조회하는 API입니다.")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "공지 상세 조회 성공"
            )
    })
    @GetMapping("/{noticeId}")
    public BaseResponse<AdminNoticeDetailResponse> getNoticeDetail(
            @PathVariable Long noticeId,
            @CurrentMember Member member) {
        return BaseResponse.onSuccess(adminNoticeAdviser.getAdminNoticeDetail(noticeId));
    }

    @Operation(summary = "[운영진용] 공지 체크 상태 조회 API", description = "[운영진용] 공지 체크 상태를 조회하는 API입니다. 공지 대상 맴버에 대해 공지 체크 표시 여부를 반환합니다.")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "공지 체크 상태 조회 성공"
            )
    })
    @Parameters({
            @Parameter(name = "noticeId", description = "공지 ID"),
            @Parameter(name = "isChecked", description = "공지 체크 표시 여부, true=공지 체크 표시 완료, false=공지 체크 표시 미완료, 생략 시 모든 맴버 조회")
    })
    @GetMapping("/{noticeId}/check-status")
    public BaseResponse<Page<AdminNoticeCheckStatusListResponse>> getNoticeCheckMembers(
            @PathVariable Long noticeId,
            Pageable pageable, // 페이징 처리
            @RequestParam(required = false) Boolean isChecked,
            @CurrentMember Member member) {
        return BaseResponse.onSuccess(adminNoticeAdviser.getNoticeCheckMemberList(noticeId, isChecked, pageable));
    }

}
