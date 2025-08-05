package umc.product.domain.notice.mapper;

import org.springframework.stereotype.Component;
import umc.product.domain.member.entity.Member;
import umc.product.domain.member.entity.enums.Part;
import umc.product.domain.notice.dto.request.admin.AdminNoticeRequest;
import umc.product.domain.notice.entity.Notice;
import umc.product.domain.notice.entity.NoticePart;
import umc.product.domain.notice.entity.NoticeSemester;
import umc.product.domain.semester.entity.Semester;

import java.util.List;

@Component
public class NoticeMapper {

    public Notice toEntity(AdminNoticeRequest request, Member writer) {
        return Notice.builder()
                .title(request.title())
                .content(request.content())
                .target(request.target())
                .hashtags(String.join(",", request.hashtags())) // 해시태그 리스트를 , 로 구분된 문자열로 변환
                .noticeDate(request.noticeDate())
                .checkDeadline(request.checkDeadline())
                .writer(writer)
                .build();
    }

    // noticePart 엔티티 생성
    private NoticePart toNoticePartEntity(Notice notice, Part part) {
        return NoticePart.builder()
                .notice(notice)
                .part(part)
                .build();
    }

    // noticeSemester 엔티티 생성
    private NoticeSemester toNoticeSemesterEntity(Notice notice, Semester semester) {
        return NoticeSemester.builder()
                .notice(notice)
                .semester(semester)
                .build();
    }

    // noticePart 엔티티 리스트 생성
    public List<NoticePart> toNoticePartEntities(Notice notice, List<Part> parts) {
        return parts.stream()
                .map(part -> toNoticePartEntity(notice, part))
                .toList();
    }

    // noticeSemester 엔티티 리스트 생성
    public List<NoticeSemester> toNoticeSemesterEntities(Notice notice, List<Semester> semesters) {
        return semesters.stream()
                .map(semester -> toNoticeSemesterEntity(notice, semester))
                .toList();
    }

}
