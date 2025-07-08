package umc.product.domain.notice.adviser;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import umc.product.domain.member.entity.Member;
import umc.product.domain.notice.converter.response.NoticeConverter;
import umc.product.domain.notice.dto.response.NoticeCheckResponse;
import umc.product.domain.notice.dto.response.NoticeDetailResponse;
import umc.product.domain.notice.dto.response.NoticeResponse;
import umc.product.domain.notice.dto.response.list.NoticeListResponse;
import umc.product.domain.notice.entity.Notice;
import umc.product.domain.notice.entity.enums.NoticeTarget;
import umc.product.domain.notice.service.NoticeService;
import umc.product.domain.noticeMember.entity.NoticeMember;
import umc.product.domain.noticeMember.service.NoticeMemberService;
import umc.product.domain.notice.status.NoticeErrorStatus;
import umc.product.global.common.exception.RestApiException;

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
        
        // 열람/미열람 인원 수 계산
        long readCount = noticeMemberService.getReadMemberCount(notice);
        long unreadCount = noticeMemberService.getUnreadMemberCount(notice);
        
        return noticeConverter.toNoticeDetailResponse(notice, noticeMember, (int) readCount, (int) unreadCount);
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