package umc.product.domain.notice.adviser;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import umc.product.domain.member.entity.Member;
import umc.product.domain.notice.converter.response.NoticeConverter;
import umc.product.domain.notice.dto.request.NoticeSearchRequest;
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
import umc.product.domain.branch.entity.Branch;
import umc.product.domain.branchUniversity.entity.BranchUniversity;
import umc.product.domain.university.entity.University;

import java.time.LocalDateTime;
import java.util.ArrayList;
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
        // 권한 기반 공지 목록 조회
        List<Notice> notices = getNoticesByPermission(member, target);
        
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

    // 권한 기반 공지 조회 (운영진이 작성한 공지 중 사용자와 동일한 소속인 것만)
    private List<Notice> getNoticesByPermission(Member member, NoticeTarget target) {
        // 1. CENTRAL 공지는 모든 사용자가 볼 수 있음
        List<Notice> centralNotices = target == null || target == NoticeTarget.CENTRAL ? 
                noticeService.findNoticesByTarget(NoticeTarget.CENTRAL) : new ArrayList<>();
        
        List<Notice> accessibleNotices = new ArrayList<>(centralNotices);
        
        // 2. BRANCH 공지 권한 체크
        if (target == null || target == NoticeTarget.BRANCH) {
            List<Notice> branchNotices = noticeService.findNoticesByTarget(NoticeTarget.BRANCH);
            List<Notice> accessibleBranchNotices = branchNotices.stream()
                    .filter(notice -> {
                        // 작성자(운영진)의 대학교에서 지부 정보를 가져와서 사용자의 지부와 비교
                        if (notice.getWriter().getUniversity() != null && member.getUniversity() != null) {
                            // 작성자의 대학교에 속한 지부들 중에서 사용자의 지부와 동일한 것이 있는지 확인
                            // isActive가 true인 branchUniversity만 필터링
                            return notice.getWriter().getUniversity().getBranchUniversityList().stream()
                                    .filter(BranchUniversity::isActive)
                                    .anyMatch(bu -> bu.getBranch().getId().equals(
                                        member.getUniversity().getBranchUniversityList().stream()
                                            .filter(BranchUniversity::isActive)
                                            .map(bu2 -> bu2.getBranch().getId())
                                            .findFirst()
                                            .orElse(null)
                                    ));
                        }
                        // 작성자의 대학교가 null이거나 사용자의 대학교가 null인 경우는 제외
                        return false;
                    })
                    .collect(Collectors.toList());
            accessibleNotices.addAll(accessibleBranchNotices);
        }
        
        // 3. UNIVERSITY 공지 권한 체크
        if (target == null || target == NoticeTarget.UNIVERSITY) {
            List<Notice> universityNotices = noticeService.findNoticesByTarget(NoticeTarget.UNIVERSITY);
            List<Notice> accessibleUniversityNotices = universityNotices.stream()
                    .filter(notice -> {
                        // 작성자(운영진)의 학교와 사용자의 학교가 동일한 경우만 조회 가능
                        if (notice.getWriter().getUniversity() != null && member.getUniversity() != null) {
                            return notice.getWriter().getUniversity().getId().equals(member.getUniversity().getId());
                        }
                        // 작성자의 학교가 null이거나 사용자의 학교가 null인 경우는 제외
                        return false;
                    })
                    .collect(Collectors.toList());
            accessibleNotices.addAll(accessibleUniversityNotices);
        }
        
        return accessibleNotices;
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

    public NoticeListResponse searchNotices(Member member, NoticeSearchRequest request, Pageable pageable) {
        // 권한 기반 공지 검색
        List<Notice> accessibleNotices = getNoticesByPermission(member, request.target());
        
        // 검색 결과를 권한에 맞게 필터링
        List<Notice> filteredNotices = accessibleNotices.stream()
                .filter(notice -> {
                    // 키워드 검색 필터링
                    if (request.keyword() != null && !request.keyword().isBlank()) {
                        String keyword = request.keyword().toLowerCase();
                        return notice.getTitle().toLowerCase().contains(keyword) ||
                               notice.getContent().toLowerCase().contains(keyword) ||
                               (notice.getHashtags() != null && notice.getHashtags().toLowerCase().contains(keyword));
                    }
                    return true;
                })
                .collect(Collectors.toList());
        
        // 페이징 처리
        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), filteredNotices.size());
        
        if (start > filteredNotices.size()) {
            return noticeConverter.toNoticeListResponse(new ArrayList<>());
        }
        
        List<Notice> pagedNotices = filteredNotices.subList(start, end);
        
        // Fetch Join을 사용하여 한 번의 쿼리로 모든 NoticeMember 조회
        List<NoticeMember> noticeMembers = noticeMemberService.getNoticeMembersByNoticeInAndMemberWithFetchJoin(pagedNotices, member);
        
        // NoticeMember를 Map으로 변환하여 빠른 조회 가능하도록 함
        Map<Long, NoticeMember> noticeMemberMap = noticeMembers.stream()
                .collect(Collectors.toMap(
                    nm -> nm.getNotice().getId(),
                    nm -> nm
                ));
        
        // 각 공지에 대해 사용자의 열람 여부 확인하여 NoticeResponse 리스트 생성
        List<NoticeResponse> noticeResponseList = pagedNotices.stream()
                .map(notice -> {
                    NoticeMember noticeMember = noticeMemberMap.get(notice.getId());
                    Boolean isRead = noticeMember != null ? noticeMember.getIsRead() : false;
                    return noticeConverter.toNoticeResponse(notice, isRead);
                })
                .collect(Collectors.toList());
        
        return noticeConverter.toNoticeListResponse(noticeResponseList);
    }
} 