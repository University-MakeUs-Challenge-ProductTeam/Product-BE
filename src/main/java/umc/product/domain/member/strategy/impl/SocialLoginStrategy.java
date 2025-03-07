package umc.product.domain.member.strategy.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import umc.product.domain.member.client.SocialMemberClient;
import umc.product.domain.member.converter.response.MemberConverter;
import umc.product.domain.member.dto.client.SocialLoginResponse;
import umc.product.domain.member.dto.request.admin.auth.AdminLoginRequest;
import umc.product.domain.member.dto.response.member.auth.MemberLoginResponse;
import umc.product.domain.member.entity.Member;
import umc.product.domain.member.entity.enums.LoginType;
import umc.product.domain.member.repository.querydsl.MemberRepository;
import umc.product.domain.member.strategy.LoginStrategy;
import umc.product.global.common.exception.RestApiException;
import umc.product.global.common.exception.code.status.AuthErrorStatus;
import umc.product.global.config.security.jwt.JwtProvider;
import umc.product.global.config.security.jwt.TokenInfo;

import static umc.product.domain.member.status.MemberErrorStatus.EMPTY_MEMBER;
import static umc.product.domain.member.status.MemberErrorStatus.NOT_SUPPORT_LOGIN_TYPE;

@RequiredArgsConstructor
@Component
public class SocialLoginStrategy implements LoginStrategy {

    private final MemberRepository memberRepository;
    private final MemberConverter memberConverter;
    private final JwtProvider jwtProvider;

    @Override
    public MemberLoginResponse login(SocialMemberClient client, String accessToken) {

        SocialLoginResponse socialLoginResponse;
        try {
            socialLoginResponse = client.getSocialLoginResponse(accessToken);

            if (socialLoginResponse == null || socialLoginResponse.id() == null) {
                throw new RestApiException(AuthErrorStatus.FAILED_SOCIAL_LOGIN);
            }

        } catch (Exception e) {
            throw new RestApiException(AuthErrorStatus.FAILED_SOCIAL_LOGIN);
        }

        String clientId = socialLoginResponse.id();
        System.out.println(clientId);

        Member member = memberRepository.findByClientIdAndLoginType(clientId, client.getLoginType())
                .orElseThrow(() -> new RestApiException(EMPTY_MEMBER));
        TokenInfo tokenInfo = generateToken(member);

        return memberConverter.toLoginMemberResponse(member, tokenInfo, member.getRole());
    }

    @Override
    public MemberLoginResponse login(SocialMemberClient client, AdminLoginRequest request) {
        throw new RestApiException(NOT_SUPPORT_LOGIN_TYPE);
    }

    private TokenInfo generateToken(Member member) {
        return jwtProvider.generateToken(member.getId().toString(), member.getRole().toString());
    }
}
