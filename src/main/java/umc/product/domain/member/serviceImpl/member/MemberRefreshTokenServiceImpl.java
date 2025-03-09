package umc.product.domain.member.serviceImpl.member;

import io.jsonwebtoken.Claims;
import org.springframework.data.redis.core.RedisTemplate;
import umc.product.domain.member.entity.RefreshToken;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import umc.product.domain.member.service.member.MemberRefreshTokenService;
import umc.product.global.config.security.jwt.JwtProvider;

import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class MemberRefreshTokenServiceImpl implements MemberRefreshTokenService {
    private final RedisTemplate<String, Object> redisTemplate;
    private final JwtProvider jwtProvider;
    private static final long REFRESH_EXPIRATION_TIME = 60*60*24*14;

    // memberId에 등록된 리프레쉬 토큰 지우고, 새로운 값 저장
    @Transactional
    public RefreshToken saveRefreshToken(String refreshToken, Long memberId) {
        String key = "refreshToken:" +memberId;
        // 이미 등록된 리프레쉬 토큰이 있다면 지우고 새로운 값 저장
        deleteRefreshToken(memberId);

        RefreshToken token = RefreshToken.builder()
                .memberId(memberId.toString())
                .refreshToken(refreshToken)
                .build();

        // 새로운 리프레쉬 토큰 저장
        redisTemplate.opsForHash().put(key, "refreshToken", token);
        redisTemplate.expire(key, REFRESH_EXPIRATION_TIME, TimeUnit.SECONDS);

        return token;
    }

    // memberId에 등록된 리프레쉬 토큰 지우기
    @Transactional
    public void deleteRefreshToken(Long memberId) {
        String key = "refreshToken:" +memberId;

        if(Boolean.TRUE.equals(redisTemplate.hasKey(key))) {
            redisTemplate.delete(key);
        }
    }

    // memberId에 맞는 리프레쉬 토큰이 존재하는지 확인
    @Transactional(readOnly = true)
    public boolean existRefreshToken(String refreshToken, Long memberId) {
        String key = "refreshToken:" + memberId;

        RefreshToken storedToken = (RefreshToken) redisTemplate.opsForHash().get(key, "refreshToken");

        return storedToken != null && storedToken.getRefreshToken().equals(refreshToken);
    }


    @Override
    public Claims getClaims(String refreshToken) {
        return jwtProvider.getClaims(refreshToken);
    }
}
