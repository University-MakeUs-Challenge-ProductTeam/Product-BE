package umc.product.domain.notice.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import umc.product.domain.notice.dto.request.admin.AdminNoticeListRequest;
import umc.product.domain.notice.entity.Notice;

public interface AdminNoticeQueryService {
    Page<Notice> getAdminNoticeList(AdminNoticeListRequest request, Pageable pageable);

    Notice getNoticeById(Long noticeId);

}
