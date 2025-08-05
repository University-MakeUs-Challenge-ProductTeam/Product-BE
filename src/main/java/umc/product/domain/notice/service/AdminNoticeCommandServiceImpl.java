package umc.product.domain.notice.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import umc.product.domain.member.entity.Member;
import umc.product.domain.member.entity.enums.Part;
import umc.product.domain.notice.dto.request.admin.AdminNoticeRequest;
import umc.product.domain.notice.entity.Notice;
import umc.product.domain.notice.entity.NoticePart;
import umc.product.domain.notice.entity.NoticeSemester;
import umc.product.domain.notice.mapper.NoticeMapper;
import umc.product.domain.notice.repository.NoticePartRepository;
import umc.product.domain.notice.repository.NoticeRepository;
import umc.product.domain.notice.repository.NoticeSemesterRepository;
import umc.product.domain.notice.status.NoticeErrorStatus;
import umc.product.domain.semester.entity.Semester;
import umc.product.global.common.exception.RestApiException;

import java.util.List;

@Service
@Transactional
@AllArgsConstructor
public class AdminNoticeCommandServiceImpl implements AdminNoticeCommandService {
    private final NoticeRepository noticeRepository;
    private final NoticeSemesterRepository noticeSemesterRepository;
    private final NoticePartRepository noticePartRepository;

    private final NoticeMapper noticeMapper;

    // [운영진용] 공지 생성
    public Notice createNotice(AdminNoticeRequest request, Member writer, List<Semester> semesters) {
        Notice notice = noticeMapper.toEntity(request, writer);

        // 파트 정보, 기수 정보 업데이트
        updateNoticePartsAndSemesters(notice, request, semesters);

        return noticeRepository.save(notice);
    }

    // [운영진용] 공지 수정
    public Notice updateNotice(Long noticeId, AdminNoticeRequest request, Member writer, List<Semester> semesters) {
        Notice notice = noticeRepository.findById(noticeId)
                .orElseThrow(() -> new RestApiException(NoticeErrorStatus.NOTICE_NOT_FOUND));

        // 해시태그 리스트에서 , 로 구분된 문자열로 변환 (엔티티에 저장할 용도)
        String hashtags = String.join(",", request.hashtags());

        // 각 필드 별 수정
        notice.update(request.title(), request.content(), request.target(), request.noticeDate(), request.checkDeadline());
        notice.updateHashtags(hashtags);

        // 파트 정보, 기수 정보 업데이트
        updateNoticePartsAndSemesters(notice, request, semesters);

        return notice;
    }

    // [운영진용] 공지 삭제
    public void deleteNotice(Long noticeId) {
        Notice notice = noticeRepository.findById(noticeId)
                .orElseThrow(() -> new RestApiException(NoticeErrorStatus.NOTICE_NOT_FOUND));

        noticeRepository.delete(notice);
    }

    // noticePart와 noticeSemester 엔티티 업데이트
    private void updateNoticePartsAndSemesters(Notice notice, AdminNoticeRequest request, List<Semester> semesters) {
        // noticePart와 noticeSemester 엔티티 생성
        List<NoticeSemester> noticeSemesters = noticeMapper.toNoticeSemesterEntities(notice, semesters);
        List<NoticePart> noticeParts = noticeMapper.toNoticePartEntities(notice, request.noticeParts());

        // 기존의 noticePart와 noticeSemester 엔티티 삭제, 업데이트
        notice.updateNoticeParts(noticeParts);
        notice.updateNoticeSemesters(noticeSemesters);
    }

}
