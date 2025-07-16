package umc.product.domain.notice.dto.response.member;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import umc.product.domain.notice.entity.enums.NoticeTarget;

@Builder
public record NoticeResponse(
    Long noticeId,
    NoticeTarget target,
    String title,
    Boolean isRead
) {
} 