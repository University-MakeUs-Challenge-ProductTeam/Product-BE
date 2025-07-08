package umc.product.domain.notice.dto.response;

import lombok.Builder;

@Builder
public record NoticeCheckResponse(
    Boolean isRead,
    Boolean isChecked
) {
} 