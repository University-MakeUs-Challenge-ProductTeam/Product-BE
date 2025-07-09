package umc.product.domain.notice.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import umc.product.domain.member.entity.Member;
import umc.product.domain.notice.dto.request.admin.AdminNoticeRequest;
import umc.product.domain.notice.entity.Notice;
import umc.product.domain.notice.mapper.NoticeMapper;
import umc.product.domain.notice.repository.NoticeRepository;
import umc.product.domain.notice.status.NoticeErrorStatus;
import umc.product.global.common.exception.RestApiException;

@Service
@Transactional
@AllArgsConstructor
public class AdminNoticeCommandServiceImpl implements AdminNoticeCommandService {
    private final NoticeRepository noticeRepository;
    private final NoticeMapper noticeMapper;

    // [운영진용] 공지 생성
    public Notice createNotice(AdminNoticeRequest request, Member writer) {
        Notice notice = noticeMapper.toEntity(request, writer);
        return noticeRepository.save(notice);
    }

    // [운영진용] 공지 수정
    public Notice updateNotice(Long noticeId, AdminNoticeRequest request, Member writer) {
        Notice notice = noticeRepository.findById(noticeId)
                .orElseThrow(() -> new RestApiException(NoticeErrorStatus.NOTICE_NOT_FOUND));

        // 해시태그 리스트에서 , 로 구분된 문자열로 변환 (엔티티에 저장할 용도)
        String hashtags = String.join(",", request.hashtags());

        // 각 필드 별 수정
        notice.update(request.title(), request.content(), request.target(), request.noticeDate(), request.checkDeadline());
        notice.updateHashtags(hashtags);
        notice.updateNoticeParts(request.noticeParts());
        notice.updateNoticeSemesters(request.noticeSemesters());

        return notice;
    }

    // [운영진용] 공지 삭제
    public void deleteNotice(Long noticeId) {
        Notice notice = noticeRepository.findById(noticeId)
                .orElseThrow(() -> new RestApiException(NoticeErrorStatus.NOTICE_NOT_FOUND));

        noticeRepository.delete(notice);
    }

}
