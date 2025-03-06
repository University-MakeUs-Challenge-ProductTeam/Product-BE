package umc.product.domain.member.dto.request.member.auth;

import umc.product.domain.member.entity.enums.Part;

public record MemberSignUpSemesterPartRequest(
        Long semesterId,
        Part part
){

}
