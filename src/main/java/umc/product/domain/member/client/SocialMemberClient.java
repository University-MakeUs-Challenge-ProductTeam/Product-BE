package umc.product.domain.member.client;

import umc.product.domain.member.dto.client.SocialLoginResponse;
import umc.product.domain.member.entity.enums.LoginType;

public interface SocialMemberClient {
    SocialLoginResponse getSocialLoginResponse(final String accessToken) throws Exception;
    LoginType getLoginType();
}
