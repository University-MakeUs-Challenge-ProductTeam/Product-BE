package umc.product.domain.member.dto.request.admin.member;

import umc.product.domain.member.entity.enums.Part;

public record AdminRegisterMemberRequest(
        String universityName,
        String nickName,
        String name,
        Part part,
        String universityPosition,
        String centralPosition
){

}
