package umc.product.domain.member.strategy;

import umc.product.domain.member.dto.request.admin.AdminLoginRequest;
import umc.product.domain.member.dto.response.common.MemberLoginResponse;

public interface LoginStrategy {
    MemberLoginResponse login(String accessToken);
    MemberLoginResponse login(AdminLoginRequest request);
}
