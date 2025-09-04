package umc.product.domain.notice.dto.response.member;

import lombok.Builder;
import umc.product.domain.event.entity.event.Event;
import umc.product.domain.notice.entity.enums.NoticeTarget;

import java.time.LocalDateTime;
import java.util.List;

@Builder
public record NoticeDetailResponse(
    Long noticeId,
    NoticeTarget target,
    String title,
    String content,
    List<String> hashtags,
    List<String> images,
    Event event,
    Long readCount,
    Long unreadCount,
    Boolean isRead,
    Boolean isChecked,
    LocalDateTime checkDeadline,
    LocalDateTime noticeDate
) {
} 