package umc.product.domain.member.dto.response.member.auth;

import lombok.Builder;

@Builder
public record MemberCreateTokenResponse(
        String accessToken,
        String refreshToken
){

}
