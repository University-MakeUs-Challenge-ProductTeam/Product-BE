package umc.product.domain.notice.service;

import umc.product.domain.notice.entity.Notice;
import umc.product.domain.notice.entity.enums.NoticeTarget;

import java.util.List;

public interface NoticeService {
    List<Notice> findNoticesByTarget(NoticeTarget target);
    List<Notice> findAllNotices();
    Notice findNoticeById(Long noticeId);
}
