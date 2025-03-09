package umc.product.domain.member.dto.request.admin.register;

import umc.product.domain.member.entity.enums.Part;

import java.util.List;

public record AdminRegisterListRequest(
        List<AdminRegisterMemberRequest> registerMemberList
){
    public record AdminRegisterMemberRequest(
            String universityName,
            String nickName,
            String name,
            Part part,
            String universityPosition,
            String centralPosition
    ){

    }
}
