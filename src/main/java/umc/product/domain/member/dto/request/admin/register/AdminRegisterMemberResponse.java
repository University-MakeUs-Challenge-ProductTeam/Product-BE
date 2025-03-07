package umc.product.domain.member.dto.request.admin.register;

import lombok.Builder;

@Builder
public record AdminRegisterMemberResponse(
        Long memberId,
        String code,
        String name,
        String nickName
){

}
