package umc.product.domain.notice.serviceImpl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import umc.product.domain.notice.entity.Notice;
import umc.product.domain.notice.entity.enums.NoticeTarget;
import umc.product.domain.notice.repository.NoticeRepository;
import umc.product.domain.notice.service.NoticeService;
import umc.product.domain.notice.status.NoticeErrorStatus;
import umc.product.global.common.exception.RestApiException;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class NoticeServiceImpl implements NoticeService {
    private final NoticeRepository noticeRepository;

    @Override
    public List<Notice> findNoticesByTarget(NoticeTarget target) {
        return noticeRepository.findByTargetOrderByNoticeDateDesc(target);
    }

    @Override
    public List<Notice> findAllNotices() {
        return noticeRepository.findAllByOrderByNoticeDateDesc();
    }

    @Override
    public Notice findNoticeById(Long noticeId) {
        return noticeRepository.findById(noticeId)
                .orElseThrow(() -> new RestApiException(NoticeErrorStatus.NOTICE_NOT_FOUND));
    }
}
