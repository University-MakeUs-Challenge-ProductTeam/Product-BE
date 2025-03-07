package umc.product.domain.member.client;

import umc.product.domain.member.dto.client.SocialLoginResponse;

public interface SocialMemberClient {
    SocialLoginResponse getSocialLoginResponse(final String accessToken) throws Exception;
}
