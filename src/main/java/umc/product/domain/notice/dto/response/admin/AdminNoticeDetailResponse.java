package umc.product.domain.notice.dto.response.admin;

import java.time.LocalDateTime;
import java.util.List;

public record AdminNoticeDetailResponse(
        Long noticeId,
        String title,
        String content,
        List<String> images,
        LocalDateTime noticeUploadDate,
        LocalDateTime checkDeadline,
        List<String> hashtags,
        Long readCount,
        Long checkCount,
        String eventTitle,
        Long eventId
) {
}
