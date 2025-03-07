package umc.product.domain.member.dto.request.admin.code;

import lombok.Builder;

@Builder
public record AdminCreateCodeResponse(
        Long memberId,
        String code,
        String name,
        String nickName
){

}
