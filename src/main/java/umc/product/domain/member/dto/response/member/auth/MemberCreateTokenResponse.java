package umc.product.domain.member.dto.response.member.auth;

import lombok.Builder;

@Builder
public record MemberCreateTokenResponse(
        Long memberId,
        String accessToken,
        String refreshToken
){

}
