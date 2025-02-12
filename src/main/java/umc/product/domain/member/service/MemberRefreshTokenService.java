package umc.product.domain.member.service;

import umc.product.domain.member.entity.Member;
import umc.product.domain.member.entity.RefreshToken;
import umc.product.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import umc.product.domain.member.repository.RefreshTokenRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberRefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;

    // memberId에 등록된 리프레쉬 토큰 지우고, 새로운 값 저장
    @Transactional
    public RefreshToken saveRefreshToken(String refreshToken, Long memberId) {
        // 이미 등록된 리프레쉬 토큰이 있다면 지우고 새로운 값 저장
        deleteRefreshToken(memberId);

        // 새로운 리프레쉬 토큰 저장
        return refreshTokenRepository.save(
                RefreshToken.builder()
                        .memberId(memberId)
                        .refreshToken(refreshToken)
                        .build()
        );
    }

    // memberId에 등록된 리프레쉬 토큰 지우기
    @Transactional
    public void deleteRefreshToken(Long memberId) {
        // 리프레쉬 토큰이 없다면 무시
        Optional<RefreshToken> refreshToken = refreshTokenRepository.findByMemberId(memberId);
        if (refreshToken.isEmpty()) {
            return;
        }
        refreshTokenRepository.delete(refreshToken.get());
    }

    // memberId에 맞는 리프레쉬 토큰이 존재하는지 확인
    @Transactional(readOnly = true)
    public boolean existRefreshToken(String refreshToken, Long memberId) {
        return refreshTokenRepository.existsByMemberIdAndRefreshToken(memberId, refreshToken);
    }
}
