package umc.product.domain.notice.adviser;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import umc.product.domain.member.entity.Member;
import umc.product.domain.notice.converter.response.NoticeConverter;
import umc.product.domain.notice.dto.response.member.NoticeCheckResponse;
import umc.product.domain.notice.dto.response.member.NoticeDetailResponse;
import umc.product.domain.notice.dto.response.member.NoticeResponse;
import umc.product.domain.notice.dto.response.member.list.NoticeListResponse;
import umc.product.domain.notice.entity.Notice;
import umc.product.domain.notice.entity.enums.NoticeTarget;
import umc.product.domain.notice.service.NoticeService;
import umc.product.domain.noticeMember.entity.NoticeMember;
import umc.product.domain.noticeMember.service.NoticeMemberService;
import umc.product.domain.notice.status.NoticeErrorStatus;
import umc.product.global.common.exception.RestApiException;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Page;
import umc.product.domain.member.service.admin.AdminMemberService;
import umc.product.domain.member.entity.enums.Role;
import umc.product.domain.member.entity.enums.Part;
import umc.product.domain.semester.service.SemesterCurrentService;
import umc.product.domain.semester.entity.Semester;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class NoticeAdviser {
    private final NoticeService noticeService;
    private final NoticeMemberService noticeMemberService;
    private final NoticeConverter noticeConverter;
    private final AdminMemberService adminMemberService;
    private final SemesterCurrentService semesterCurrentService;

    public NoticeListResponse getNoticeList(Member member, NoticeTarget target) {
        // 공지 목록 조회 (target이 null이면 모든 공지, 아니면 해당 대상의 공지)
        List<Notice> notices = target != null ? 
                noticeService.findNoticesByTarget(target) : 
                noticeService.findAllNotices();
        
        // Fetch Join을 사용하여 한 번의 쿼리로 모든 NoticeMember 조회
        List<NoticeMember> noticeMembers = noticeMemberService.getNoticeMembersByNoticeInAndMemberWithFetchJoin(notices, member);
        
        // NoticeMember를 Map으로 변환하여 빠른 조회 가능하도록 함
        Map<Long, NoticeMember> noticeMemberMap = noticeMembers.stream()
                .collect(Collectors.toMap(
                    nm -> nm.getNotice().getId(),
                    nm -> nm
                ));
        
        // 각 공지에 대해 사용자의 열람 여부 확인하여 NoticeResponse 리스트 생성
        List<NoticeResponse> noticeResponseList = notices.stream()
                .map(notice -> {
                    NoticeMember noticeMember = noticeMemberMap.get(notice.getId());
                    Boolean isRead = noticeMember != null ? noticeMember.getIsRead() : false;
                    return noticeConverter.toNoticeResponse(notice, isRead);
                })
                .collect(Collectors.toList());
        
        return noticeConverter.toNoticeListResponse(noticeResponseList);
    }

    public NoticeDetailResponse getNoticeDetail(Member member, Long noticeId) {
        // 공지 상세 정보 조회
        Notice notice = noticeService.findNoticeById(noticeId);
        
        // 사용자의 열람 정보 조회
        NoticeMember noticeMember = noticeMemberService.getNoticeMemberByNoticeAndMember(notice, member);

        // 공지 타겟
        NoticeTarget target = notice.getTarget();
        // 현재 기수 조회
        Semester currentSemester = semesterCurrentService.getCurrentSemester();
        Long semesterId = currentSemester.getId();
        Role role = null;
        if (target.equals(NoticeTarget.CENTRAL)) {
            role = Role.CENTRAL_ADMIN;
        } else if (target.equals(NoticeTarget.BRANCH)) {
            role = Role.BRANCH_STAFF;
        } else if (target.equals(NoticeTarget.UNIVERSITY)) {
            role = Role.UNIVERSITY_STAFF;
        } else {
            throw new RestApiException(NoticeErrorStatus.WRONG_NOTICE_TARGET);
        }
        // 전체 대상자 수 조회 (unpaged)
        long totalCount = adminMemberService.countMemberByFilter(null, semesterId, role, null);
        // 열람/미열람 인원 수 계산
        long readCount = noticeMemberService.getReadMemberCount(notice);
        long unreadCount = totalCount - readCount;
        return noticeConverter.toNoticeDetailResponse(notice, noticeMember, readCount, unreadCount);
    }

    public NoticeCheckResponse checkNotice(Member member, Long noticeId) {
        // 공지 조회
        Notice notice = noticeService.findNoticeById(noticeId);
        
        // 사용자의 열람 정보 조회 또는 생성
        NoticeMember noticeMember = noticeMemberService.getNoticeMemberByNoticeAndMember(notice, member);
        if (noticeMember == null) {
            noticeMember = noticeMemberService.createNoticeMember(notice, member);
        }
        
        // 마감 기한 확인
        if (notice.getCheckDeadline() != null && LocalDateTime.now().isAfter(notice.getCheckDeadline())) {
            throw new RestApiException(NoticeErrorStatus.CHECK_DEADLINE_EXPIRED);
        }
        
        // 열람 상태와 수동 확인 상태를 모두 true로 설정
        noticeMemberService.markAsRead(noticeMember);
        noticeMemberService.markAsChecked(noticeMember);
        
        return noticeConverter.toNoticeCheckResponse(noticeMember);
    }
} 