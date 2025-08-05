package umc.product.domain.notice.repository.querydsl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import umc.product.domain.notice.dto.request.admin.AdminNoticeListRequest;
import umc.product.domain.notice.entity.Notice;

public interface NoticeDslRepository {
    Page<Notice> searchAdminNotices(AdminNoticeListRequest request, Pageable pageable);
}