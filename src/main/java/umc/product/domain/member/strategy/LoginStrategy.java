package umc.product.domain.member.strategy;

import umc.product.domain.member.dto.response.MemberLoginResponse;

public interface
LoginStrategy {
    MemberLoginResponse login(String accessToken);
}
