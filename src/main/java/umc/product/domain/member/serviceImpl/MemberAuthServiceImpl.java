package umc.product.domain.member.serviceImpl;

import umc.product.domain.member.dto.request.MemberLoginRequest;
import umc.product.domain.member.entity.Member;
import umc.product.domain.member.entity.enums.LoginType;
import umc.product.domain.member.dto.response.MemberGenerateTokenResponse;
import umc.product.domain.member.dto.response.MemberIdResponse;
import umc.product.domain.member.dto.response.MemberLoginResponse;
import umc.product.domain.member.service.MemberAuthService;
import umc.product.domain.member.strategy.context.LoginContext;
import umc.product.global.common.exception.RestApiException;
import umc.product.global.common.exception.code.status.AuthErrorStatus;
import umc.product.global.config.security.jwt.JwtProvider;
import umc.product.global.config.security.jwt.TokenInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberAuthServiceImpl implements MemberAuthService {

    public final MemberServiceImpl memberService;
    public final MemberRefreshTokenServiceImpl refreshTokenService;

    public final JwtProvider jwtTokenProvider;
    private final LoginContext loginContext;

    // 소셜 로그인을 수행하는 함수
    @Override
    @Transactional
    public MemberLoginResponse socialLogin(String accessToken, LoginType loginType) {

        // 소셜 로그인 수행
        MemberLoginResponse response = loginContext.executeStrategy(accessToken, loginType);

        // 리프레쉬 토큰 저장
        refreshTokenService.saveRefreshToken(response.getRefreshToken(), response.getMemberId());

        return loginContext.executeStrategy(accessToken, loginType);
    }

    // 자체 로그인을 수행하는 함수
    @Override
    @Transactional(readOnly = true)
    public MemberLoginResponse login(MemberLoginRequest request) {

        // 로그인 수행
        MemberLoginResponse response = loginContext.executeStrategy(request);

        // 리프레쉬 토큰 저장
        refreshTokenService.saveRefreshToken(response.getRefreshToken(), response.getMemberId());

        return loginContext.executeStrategy(request);
    }

    // 새로운 액세스 토큰 발급 함수
    @Override
    @Transactional
    public MemberGenerateTokenResponse generateNewAccessToken(String refreshToken, Member member) {

        Member loginMember = memberService.findById(member.getId());

        // 만료된 refreshToken인지 확인
        if (!jwtTokenProvider.validateToken(refreshToken))
            throw new RestApiException(AuthErrorStatus.EXPIRED_REFRESH_TOKEN);

        // 디비에 저장된 refreshToken과 동일하지 않다면 유효하지 않음
        if (!refreshTokenService.existRefreshToken(refreshToken, loginMember.getId()))
            throw new RestApiException(AuthErrorStatus.INVALID_REFRESH_TOKEN);

        // 토큰 발행
        TokenInfo tokenInfo = jwtTokenProvider.generateToken(loginMember.getId().toString(), member.getRole().toString());

        // 리프레쉬 토큰 저장
        refreshTokenService.saveRefreshToken(tokenInfo.refreshToken(), loginMember.getId());

        return new MemberGenerateTokenResponse(tokenInfo.accessToken(), tokenInfo.refreshToken());
    }

    // 로그아웃 함수
    @Override
    @Transactional
    public MemberIdResponse logout(Member member) {

        // refreshToken 삭제
        refreshTokenService.deleteRefreshToken(member.getId());

        return new MemberIdResponse(member.getId());
    }

    // 회원 탈퇴 함수
    @Override
    @Transactional
    public MemberIdResponse withdrawal(Member member) {

        // refreshToken 삭제
        refreshTokenService.deleteRefreshToken(member.getId());

        // 멤버 soft delete
        member.delete();

        return new MemberIdResponse(member.getId());
    }
}
