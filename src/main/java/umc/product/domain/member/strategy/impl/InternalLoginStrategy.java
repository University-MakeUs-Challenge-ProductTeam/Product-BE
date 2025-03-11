package umc.product.domain.member.strategy.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import umc.product.domain.member.client.SocialMemberClient;
import umc.product.domain.member.converter.response.MemberConverter;
import umc.product.domain.member.dto.request.admin.auth.AdminLoginRequest;
import umc.product.domain.member.dto.response.member.auth.MemberLoginResponse;
import umc.product.domain.member.entity.Member;
import umc.product.domain.member.repository.querydsl.MemberDslRepository;
import umc.product.domain.member.strategy.LoginStrategy;
import umc.product.global.common.exception.RestApiException;
import umc.product.global.config.security.jwt.JwtProvider;
import umc.product.global.config.security.jwt.TokenInfo;

import static umc.product.domain.member.status.MemberErrorStatus.*;

@Component
@RequiredArgsConstructor
public class InternalLoginStrategy implements LoginStrategy {
    private final MemberDslRepository memberDslRepository;
    private final MemberConverter memberConverter;
    private final JwtProvider jwtProvider;
    // Spring Security PasswordEncoder 사용
    private final PasswordEncoder passwordEncoder;

    @Override
    public MemberLoginResponse login(
            SocialMemberClient client,
            String accessToken
    ) {
        throw new RestApiException(UNSUPPORTED_LOGIN_TYPE);
    }

    @Override
    public MemberLoginResponse login(
            SocialMemberClient client,
            AdminLoginRequest request
    ) {
        // 회원 조회
        Member member = memberDslRepository.findMemberByClientId(request.clientId())
                .orElseThrow(() -> new RestApiException(AUTHENTICATION_FAILED));

        if (!passwordEncoder.matches(request.password(), member.getMemberLoginInfo().getPassword())) {
            throw new RestApiException(INCORRECT_PASSWORD);
        }

        // JWT 토큰 생성
        TokenInfo tokenInfo = generateToken(member);

        return memberConverter.toLoginMemberResponse(member, tokenInfo, member.getRole());
    }

    private TokenInfo generateToken(Member member) {
        return jwtProvider.generateToken(member.getId().toString(), member.getRole().toString());
    }
}
