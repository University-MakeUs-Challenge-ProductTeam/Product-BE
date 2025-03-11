package umc.product.domain.member.strategy.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import umc.product.domain.member.client.SocialMemberClient;
import umc.product.domain.member.converter.response.MemberConverter;
import umc.product.domain.member.dto.client.SocialLoginResponse;
import umc.product.domain.member.dto.request.admin.auth.AdminLoginRequest;
import umc.product.domain.member.dto.response.member.auth.MemberLoginResponse;
import umc.product.domain.member.entity.Member;
import umc.product.domain.member.repository.querydsl.MemberDslRepository;
import umc.product.domain.member.strategy.LoginStrategy;
import umc.product.global.common.exception.RestApiException;
import umc.product.domain.member.status.AuthErrorStatus;
import umc.product.global.config.security.jwt.JwtProvider;
import umc.product.global.config.security.jwt.TokenInfo;

import static umc.product.domain.member.status.MemberErrorStatus.*;

@RequiredArgsConstructor
@Component
public class SocialLoginStrategy implements LoginStrategy {

    private final MemberDslRepository memberDslRepository;
    private final MemberConverter memberConverter;
    private final JwtProvider jwtProvider;

    /**
     * GOOGLE, KAKAO는 따로 구현하는 것이 아닌 공통적인 login 메서드 사용
     * SocialMemberClient로 Social을 구분
     */
    @Override
    public MemberLoginResponse login(
            SocialMemberClient client,
            String accessToken
    ) {
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

        Member member = memberDslRepository.findByClientIdAndLoginType(clientId, client.getLoginType())
                .orElseThrow(() -> new RestApiException(MEMBER_NOT_FOUND));
        TokenInfo tokenInfo = generateToken(member);

        return memberConverter.toLoginMemberResponse(member, tokenInfo, member.getRole());
    }

    @Override
    public MemberLoginResponse login(
            SocialMemberClient client,
            AdminLoginRequest request
    ) {
        throw new RestApiException(UNSUPPORTED_LOGIN_TYPE);
    }

    private TokenInfo generateToken(Member member) {
        return jwtProvider.generateToken(member.getId().toString(), member.getRole().toString());
    }
}
