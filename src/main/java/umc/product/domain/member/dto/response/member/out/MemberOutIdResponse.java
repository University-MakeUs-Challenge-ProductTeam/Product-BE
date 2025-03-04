package umc.product.domain.member.dto.response.member.out;

import lombok.Builder;

@Builder
public record MemberOutIdResponse(
        Long memberId,
        Long outId

){
}
