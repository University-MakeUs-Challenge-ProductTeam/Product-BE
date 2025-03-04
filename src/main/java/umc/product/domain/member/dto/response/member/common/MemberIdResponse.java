package umc.product.domain.member.dto.response.member.common;

import lombok.Builder;

@Builder
public record MemberIdResponse (
        Long memberId
){

}
