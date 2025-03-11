package umc.product.domain.member.strategy;

import umc.product.domain.member.client.SocialMemberClient;
import umc.product.domain.member.dto.request.admin.auth.AdminLoginRequest;
import umc.product.domain.member.dto.response.member.auth.MemberLoginResponse;

/**
 * LoginStrategy는 Internal(web), Social(app)의 interface
 * SocialMemberClient를 받아 Social과 맞는 Login 로직을 수행
 */
public interface LoginStrategy {
    MemberLoginResponse login(SocialMemberClient client,
                              String accessToken);
    MemberLoginResponse login(SocialMemberClient client,
                              AdminLoginRequest request);
}
