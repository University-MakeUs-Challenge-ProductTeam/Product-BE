package umc.product.domain.notice.dto.request.admin;

import umc.product.domain.member.entity.enums.Part;
import umc.product.domain.notice.entity.enums.NoticeTarget;

public record AdminNoticeListRequest(
        String keyword,
        NoticeTarget target,
        Long semesterIds,
        Part parts,
        Integer eventMonth
) {
}
