package umc.product.domain.member.dto.request.admin.member;

import umc.product.domain.member.entity.enums.Part;

public record AdminUpdateSemesterPartRequest(
        Long semesterPartId,
        Long semesterId,
        Part part
) {

}
