package umc.product.domain.notice.dto.response.member.list;

import lombok.Builder;

@Builder
public record NoticeCheckResponse(
    Boolean isRead,
    Boolean isChecked
) {
} 