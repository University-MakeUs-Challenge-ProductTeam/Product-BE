package umc.product.domain.member.service.common;

import io.jsonwebtoken.Claims;
import umc.product.domain.member.entity.RefreshToken;

public interface MemberRefreshTokenService {
    public RefreshToken saveRefreshToken(String refreshToken, Long memberId);
    public void deleteRefreshToken(Long memberId);
    public boolean existRefreshToken(String refreshToken, Long memberId);
    public Claims getClaims(String refreshToken);
}
