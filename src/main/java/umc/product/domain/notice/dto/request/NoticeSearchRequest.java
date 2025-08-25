package umc.product.domain.notice.dto.request;

import umc.product.domain.notice.entity.enums.NoticeTarget;

public record NoticeSearchRequest(
        String keyword,
        NoticeTarget target
) {
}
