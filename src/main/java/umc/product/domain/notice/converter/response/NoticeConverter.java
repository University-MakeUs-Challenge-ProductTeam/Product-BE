package umc.product.domain.notice.converter.response;

import org.springframework.stereotype.Component;
import umc.product.domain.notice.dto.response.member.NoticeCheckResponse;
import umc.product.domain.notice.dto.response.member.NoticeDetailResponse;
import umc.product.domain.notice.dto.response.member.NoticeResponse;
import umc.product.domain.notice.dto.response.member.list.NoticeListResponse;
import umc.product.domain.notice.entity.Notice;
import umc.product.domain.noticeMember.entity.NoticeMember;

import java.util.List;

@Component
public class NoticeConverter {
    
    public NoticeCheckResponse toNoticeCheckResponse(NoticeMember noticeMember) {
        return NoticeCheckResponse.builder()
                .isRead(noticeMember.getIsRead())
                .isChecked(noticeMember.getIsChecked())
                .build();
    }
    
    public NoticeResponse toNoticeResponse(Notice notice, Boolean isRead) {
        return NoticeResponse.builder()
                .noticeId(notice.getId())
                .title(notice.getTitle())
                .target(notice.getTarget())
                .isRead(isRead)
                .build();
    }
    
    public NoticeListResponse toNoticeListResponse(List<NoticeResponse> noticeResponseList) {
        return NoticeListResponse.builder()
                .noticeResponseList(noticeResponseList)
                .build();
    }
    
    public NoticeDetailResponse toNoticeDetailResponse(Notice notice, NoticeMember noticeMember, Long readCount, Long unreadCount) {
        return NoticeDetailResponse.builder()
                .noticeId(notice.getId())
                .title(notice.getTitle())
                .content(notice.getContent())
                .target(notice.getTarget())
                .checkDeadline(notice.getCheckDeadline())
                .noticeDate(notice.getCreatedAt())
                .isRead(noticeMember != null ? noticeMember.getIsRead() : false)
                .readCount(readCount)
                .unreadCount(unreadCount)
                .build();
    }
} 