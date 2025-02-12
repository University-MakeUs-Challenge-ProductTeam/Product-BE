package umc.product.domain.member.strategy;

import umc.product.domain.member.dto.request.MemberLoginRequest;
import umc.product.domain.member.dto.response.MemberLoginResponse;

public interface LoginStrategy {
    MemberLoginResponse login(String accessToken);
    MemberLoginResponse login(MemberLoginRequest request);
}
