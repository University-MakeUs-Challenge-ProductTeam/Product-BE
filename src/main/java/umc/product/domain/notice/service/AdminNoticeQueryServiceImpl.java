package umc.product.domain.notice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import umc.product.domain.notice.dto.request.admin.AdminNoticeListRequest;
import umc.product.domain.notice.entity.Notice;
import umc.product.domain.notice.repository.NoticeRepository;
import umc.product.domain.notice.repository.querydsl.NoticeDslRepository;
import umc.product.domain.notice.status.NoticeErrorStatus;
import umc.product.global.common.exception.RestApiException;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AdminNoticeQueryServiceImpl implements AdminNoticeQueryService {

    private final NoticeDslRepository noticeDslRepository;
    private final NoticeRepository noticeRepository;

    // 운영진용 공지 목록 조회
    @Override
    public Page<Notice> getAdminNoticeList(AdminNoticeListRequest request, Pageable pageable) {
        return noticeDslRepository.searchAdminNotices(request, pageable);
    }

    // 운영진용 공지 상세 조회
    @Override
    public Notice getNoticeById(Long noticeId) {
        return noticeRepository.findById(noticeId)
                .orElseThrow(() -> new RestApiException(NoticeErrorStatus.NOTICE_NOT_FOUND));
    }

}
