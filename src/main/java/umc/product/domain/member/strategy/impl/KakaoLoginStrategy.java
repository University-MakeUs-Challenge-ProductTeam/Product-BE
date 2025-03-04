package umc.product.domain.member.strategy.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import umc.product.domain.member.client.KakaoMemberClient;
import umc.product.domain.member.converter.response.MemberConverter;
import umc.product.domain.member.dto.client.KakaoResponse;
import umc.product.domain.member.dto.request.admin.auth.AdminLoginRequest;
import umc.product.domain.member.dto.response.member.auth.MemberLoginResponse;
import umc.product.domain.member.entity.enums.LoginType;
import umc.product.domain.member.entity.Member;
import umc.product.domain.member.entity.enums.Role;
import umc.product.domain.member.mapper.MemberMapper;
import umc.product.domain.member.repository.MemberRepository;
import umc.product.domain.member.serviceImpl.member.MemberServiceImpl;
import umc.product.domain.member.strategy.LoginStrategy;
import umc.product.global.common.exception.RestApiException;
import umc.product.global.common.exception.code.status.AuthErrorStatus;
import umc.product.global.config.security.jwt.JwtProvider;
import umc.product.global.config.security.jwt.TokenInfo;

import java.util.Optional;

@RequiredArgsConstructor
@Component
public class KakaoLoginStrategy implements LoginStrategy {

    private final MemberRepository memberRepository;
    private final MemberConverter memberConverter;
    private final MemberMapper memberMapper;
    private final MemberServiceImpl memberService;
    private final JwtProvider jwtProvider;
    private final KakaoMemberClient kakaoMemberClient;

    @Override
    public MemberLoginResponse login(String accessToken) {

        KakaoResponse kakaoResponse;
        try {
            kakaoResponse = kakaoMemberClient.getkakaoResponse(accessToken);

            if (kakaoResponse == null || kakaoResponse.getId() == null) {
                throw new RestApiException(AuthErrorStatus.FAILED_SOCIAL_LOGIN);
            }

        } catch (WebClientResponseException.Unauthorized e) {
            throw new RestApiException(AuthErrorStatus.FAILED_SOCIAL_LOGIN);
        }

        // Kakao-specific logic
        String clientId = kakaoResponse.getId();

        Optional<Member> getMember = memberRepository.findByClientIdAndLoginType(clientId, LoginType.KAKAO);

        if (getMember.isEmpty()) {
            return saveNewMember(clientId, LoginType.KAKAO);
        }

        Member member = getMember.get();
        TokenInfo tokenInfo = generateToken(member);

        return memberConverter.toLoginMemberResponse(member, tokenInfo, member.getRole());
    }

    @Override
    public MemberLoginResponse login(AdminLoginRequest request) {
        // todo : MemberLoginRequest  방식은 지원하지 않습니다. RestApiException으로 변경
        throw new UnsupportedOperationException("MemberLoginRequest  방식은 지원하지 않습니다.");
    }

    private MemberLoginResponse saveNewMember(String clientId, LoginType loginType) {
        Member member = memberMapper.toMember(clientId, loginType);
        member.changeRole(Role.GUEST);
        Member newMember = memberService.saveEntity(member);
        TokenInfo tokenInfo = generateToken(newMember);
        return memberConverter.toLoginMemberResponse(newMember, tokenInfo, Role.GUEST);
    }

    private TokenInfo generateToken(Member member) {
        return jwtProvider.generateToken(member.getId().toString(), member.getRole().toString());
    }
}
