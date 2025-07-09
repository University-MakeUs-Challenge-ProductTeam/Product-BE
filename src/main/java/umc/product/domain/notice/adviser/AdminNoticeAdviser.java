package umc.product.domain.notice.adviser;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import umc.product.domain.notice.converter.response.AdminNoticeConverter;
import umc.product.domain.notice.dto.request.admin.AdminNoticeListRequest;
import umc.product.domain.notice.dto.response.admin.AdminNoticeResponse;
import umc.product.domain.notice.service.AdminNoticeService;
import umc.product.domain.noticeMember.service.NoticeMemberService;

@Component
@RequiredArgsConstructor
public class AdminNoticeAdviser {

    private final AdminNoticeService adminNoticeService;
    private final NoticeMemberService noticeMemberService;

    private final AdminNoticeConverter converter;

    // [운영진용] 공지 목록 조회
    public Page<AdminNoticeResponse> getAdminNoticeList(AdminNoticeListRequest request, Pageable pageable) {
        return adminNoticeService.getAdminNoticeList(request, pageable)
                .map(notice -> {
                    Long readCount = noticeMemberService.getReadMemberCount(notice);
                    return converter.toAdminNoticeResponse(notice, readCount);
                });
    }
}
