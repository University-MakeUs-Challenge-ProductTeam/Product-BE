package umc.product.domain.member.dto.response.member.code;

import lombok.Builder;

@Builder
public record MemberCreateCodeResponse(
        Long memberId,
        String code,
        String name,
        String nickName
){

}
