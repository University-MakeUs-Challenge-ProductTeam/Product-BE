package umc.product.domain.notice.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import umc.product.domain.notice.dto.request.NoticeSearchRequest;
import umc.product.domain.notice.entity.Notice;
import umc.product.domain.notice.entity.enums.NoticeTarget;

import java.util.List;

public interface NoticeService {
    List<Notice> findNoticesByTarget(NoticeTarget target);
    List<Notice> findAllNotices();
    Notice findNoticeById(Long noticeId);
    Page<Notice> searchNotices(NoticeSearchRequest request, Pageable pageable);
}
