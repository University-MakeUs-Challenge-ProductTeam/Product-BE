package umc.product.domain.member.dto.request.admin.member;


import umc.product.domain.member.entity.enums.Part;

public record AdminInsertSemesterPartRequest(
        Long semesterId,
        Part part
) {

}
