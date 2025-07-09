package umc.product.domain.notice.dto.request.admin;

import umc.product.domain.notice.entity.NoticePart;
import umc.product.domain.notice.entity.NoticeSemester;
import umc.product.domain.notice.entity.enums.NoticeTarget;

import java.time.LocalDateTime;
import java.util.List;

public record AdminNoticeRequest(
        String title,
        String content,
        NoticeTarget target,
        List<String> hashtags,
        List<NoticeSemester> noticeSemesters,
        List<NoticePart> noticeParts,
        LocalDateTime noticeDate,
        LocalDateTime checkDeadline
        ) {
}
