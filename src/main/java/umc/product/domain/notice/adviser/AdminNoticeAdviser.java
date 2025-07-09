package umc.product.domain.notice.adviser;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import umc.product.domain.member.entity.Member;
import umc.product.domain.notice.converter.response.AdminNoticeConverter;
import umc.product.domain.notice.dto.request.admin.AdminNoticeListRequest;
import umc.product.domain.notice.dto.response.admin.AdminNoticeCheckStatusResponse;
import umc.product.domain.notice.dto.response.admin.AdminNoticeDetailResponse;
import umc.product.domain.notice.dto.response.admin.AdminNoticeResponse;
import umc.product.domain.notice.dto.response.admin.list.AdminNoticeCheckStatusListResponse;
import umc.product.domain.notice.entity.Notice;
import umc.product.domain.notice.service.AdminNoticeQueryService;
import umc.product.domain.noticeMember.entity.NoticeMember;
import umc.product.domain.noticeMember.service.AdminNoticeMemberQueryService;

import java.util.List;

@Component
@RequiredArgsConstructor
public class AdminNoticeAdviser {

    private final AdminNoticeQueryService adminNoticeQueryService;
    private final AdminNoticeMemberQueryService adminNoticeMemberQueryService;


    private final AdminNoticeConverter converter;

    // [운영진용] 공지 목록 조회
    public Page<AdminNoticeResponse> getAdminNoticeList(AdminNoticeListRequest request, Pageable pageable) {
        return adminNoticeQueryService.getAdminNoticeList(request, pageable)
                .map(notice -> {
                    Long readCount = adminNoticeMemberQueryService.getReadMemberCount(notice);
                    return converter.toAdminNoticeResponse(notice, readCount);
                });
    }

    // [운영진용] 공지 상세 조회
    public AdminNoticeDetailResponse getAdminNoticeDetail(Long noticeId) {
        Notice notice = adminNoticeQueryService.getNoticeById(noticeId);

        Long readCount = adminNoticeMemberQueryService.getReadMemberCount(notice);
        Long checkCount = adminNoticeMemberQueryService.getCheckMemberCount(notice); // 열람 체크 수

        return converter.toAdminNoticeDetailResponse(notice, readCount, checkCount);
    }

    // [운영진용] 공지 열람 체크
    public Page<AdminNoticeCheckStatusListResponse> getNoticeCheckMemberList(Long noticeId, Boolean isChecked, Pageable pageable) {
        // 공지 조회
        Notice notice = adminNoticeQueryService.getNoticeById(noticeId);

        // 대상 멤버 조회
        Page<Member> targetMembers = adminNoticeMemberQueryService.getNoticeTargetMembers(notice, isChecked, pageable);

        Long checkCount = adminNoticeMemberQueryService.getCheckMemberCount(notice); // 열람 체크 수

        return converter.toAdminNoticeCheckStatusResponsePage(targetMembers, notice, checkCount);
    }
}
