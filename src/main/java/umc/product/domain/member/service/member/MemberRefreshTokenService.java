package umc.product.domain.member.service.member;

import io.jsonwebtoken.Claims;
import umc.product.domain.member.entity.RefreshToken;

public interface MemberRefreshTokenService {
    RefreshToken saveRefreshToken(String refreshToken,
                                  Long memberId);
    void deleteRefreshToken(Long memberId);
    boolean existRefreshToken(String refreshToken,
                              Long memberId);
    Claims getClaims(String refreshToken);
}
