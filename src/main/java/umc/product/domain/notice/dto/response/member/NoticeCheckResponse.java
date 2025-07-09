package umc.product.domain.notice.dto.response.member;

import lombok.Builder;

@Builder
public record NoticeCheckResponse(
    Boolean isRead,
    Boolean isChecked
) {
} 