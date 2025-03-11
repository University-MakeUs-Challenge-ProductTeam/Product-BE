package umc.product.domain.member.serviceImpl.member;

import io.jsonwebtoken.Claims;
import org.springframework.data.redis.core.RedisTemplate;
import umc.product.domain.member.entity.RefreshToken;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import umc.product.domain.member.repository.redis.MemberRedisRepository;
import umc.product.domain.member.service.member.MemberRefreshTokenService;
import umc.product.global.config.security.jwt.JwtProvider;

import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class MemberRefreshTokenServiceImpl implements MemberRefreshTokenService {
    private final MemberRedisRepository memberRedisRepository;
    private final JwtProvider jwtProvider;

    // memberId에 등록된 리프레쉬 토큰 지우고, 새로운 값 저장
    @Transactional
    public RefreshToken saveRefreshToken(String refreshToken, Long memberId) {
        return memberRedisRepository.saveRefreshToken(refreshToken, memberId);
    }

    // memberId에 등록된 리프레쉬 토큰 지우기
    @Transactional
    public void deleteRefreshToken(Long memberId) {
        memberRedisRepository.deleteRefreshToken(memberId);
    }

    // memberId에 맞는 리프레쉬 토큰이 존재하는지 확인
    @Transactional(readOnly = true)
    public boolean existRefreshToken(String refreshToken, Long memberId) {
        return memberRedisRepository.existRefreshToken(refreshToken, memberId);
    }


    @Override
    public Claims getClaims(String refreshToken) {
        return jwtProvider.getClaims(refreshToken);
    }
}
