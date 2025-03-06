package umc.product.domain.member.strategy.impl;


import umc.product.domain.member.converter.response.MemberConverter;
import umc.product.domain.member.dto.request.admin.auth.AdminLoginRequest;
import umc.product.domain.member.entity.enums.LoginType;
import umc.product.domain.member.entity.Member;
import umc.product.domain.member.entity.enums.Role;
import umc.product.domain.member.dto.response.member.auth.MemberLoginResponse;
import umc.product.domain.member.mapper.MemberMapper;
import umc.product.domain.member.repository.querydsl.MemberRepository;
import umc.product.domain.member.serviceImpl.member.MemberServiceImpl;
import umc.product.domain.member.strategy.LoginStrategy;
import umc.product.global.common.exception.RestApiException;
import umc.product.global.config.security.jwt.JwtProvider;
import umc.product.global.config.security.jwt.TokenInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

import static umc.product.domain.member.status.MemberErrorStatus.NOT_SUPPORT_LOGIN_TYPE;

@RequiredArgsConstructor
@Component
public class AnonymousLoginStrategy implements LoginStrategy {

    private final MemberRepository memberRepository;
    private final MemberConverter memberConverter;
    private final MemberMapper memberMapper;
    private final MemberServiceImpl memberService;
    private final JwtProvider jwtProvider;

    @Override
    public MemberLoginResponse login(String accessToken) {
        // Anonymous-specific logic
        Optional<Member> getMember = memberRepository.findByClientIdAndLoginType(accessToken, LoginType.ANONYMOUS);

        if (getMember.isEmpty()) {
            return saveNewMember(accessToken, LoginType.ANONYMOUS);
        }

        Member member = getMember.get();
        boolean isServiceMember = member.getName() != null;
        TokenInfo tokenInfo = generateToken(member);

        return memberConverter.toLoginMemberResponse(member, tokenInfo, member.getRole());
    }

    @Override
    public MemberLoginResponse login(AdminLoginRequest request) {
        throw new RestApiException(NOT_SUPPORT_LOGIN_TYPE);
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

