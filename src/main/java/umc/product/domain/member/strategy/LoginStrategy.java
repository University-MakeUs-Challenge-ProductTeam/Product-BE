package umc.product.domain.member.strategy;

import umc.product.domain.member.dto.request.admin.auth.AdminLoginRequest;
import umc.product.domain.member.dto.response.member.auth.MemberLoginResponse;

public interface LoginStrategy {
    MemberLoginResponse login(String accessToken);
    MemberLoginResponse login(AdminLoginRequest request);
}
