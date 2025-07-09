package umc.product.domain.notice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import umc.product.domain.notice.dto.request.admin.AdminNoticeListRequest;
import umc.product.domain.notice.entity.Notice;
import umc.product.domain.notice.repository.querydsl.NoticeDslRepository;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AdminNoticeServiceImpl implements AdminNoticeService {

    private final NoticeDslRepository noticeDslRepository;

    // 운영진용 공지 목록 조회
    @Override
    public Page<Notice> getAdminNoticeList(AdminNoticeListRequest request, Pageable pageable) {
        return noticeDslRepository.searchAdminNotices(request, pageable);
    }
}
