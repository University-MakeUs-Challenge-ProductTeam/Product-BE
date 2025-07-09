package umc.product.domain.notice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import umc.product.domain.member.entity.Member;
import umc.product.domain.member.entity.enums.Part;
import umc.product.domain.notice.dto.request.admin.AdminNoticeListRequest;
import umc.product.domain.notice.dto.response.admin.AdminNoticeCheckStatusResponse;
import umc.product.domain.notice.entity.Notice;
import umc.product.domain.notice.repository.NoticeRepository;
import umc.product.domain.notice.repository.querydsl.NoticeDslRepository;
import umc.product.domain.notice.status.NoticeErrorStatus;
import umc.product.domain.noticeMember.entity.NoticeMember;
import umc.product.domain.semester.entity.Semester;
import umc.product.global.common.exception.RestApiException;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AdminNoticeQueryServiceImpl implements AdminNoticeQueryService {

    private final NoticeDslRepository noticeDslRepository;
    private final NoticeRepository noticeRepository;

    // 운영진용 공지 목록 조회 todo 일반 사용자용과 통합
    @Override
    public Page<Notice> getAdminNoticeList(AdminNoticeListRequest request, Pageable pageable) {
        return noticeDslRepository.searchAdminNotices(request, pageable);
    }

    // 운영진용 공지 조회 todo 일반 사용자용과 통합
    @Override
    public Notice getNoticeById(Long noticeId) {
        return noticeRepository.findById(noticeId)
                .orElseThrow(() -> new RestApiException(NoticeErrorStatus.NOTICE_NOT_FOUND));
    }
}
