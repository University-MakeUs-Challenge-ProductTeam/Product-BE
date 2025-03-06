package umc.product.domain.member.dto.response.member.code;

import lombok.Builder;

@Builder
public record MemberCodeVerifyResponse (
        Long memberId,
        String name,
        String nickName
){

}
