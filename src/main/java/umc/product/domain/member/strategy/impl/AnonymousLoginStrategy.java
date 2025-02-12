package umc.product.domain.member.strategy.impl;


import umc.product.domain.member.dto.request.MemberLoginRequest;
import umc.product.domain.member.entity.LoginType;
import umc.product.domain.member.entity.Member;
import umc.product.domain.member.entity.Role;
import umc.product.domain.member.dto.response.MemberLoginResponse;
import umc.product.domain.member.mapper.MemberMapper;
import umc.product.domain.member.repository.MemberRepository;
import umc.product.domain.member.service.MemberService;
import umc.product.domain.member.strategy.LoginStrategy;
import umc.product.global.config.security.jwt.JwtProvider;
import umc.product.global.config.security.jwt.TokenInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@RequiredArgsConstructor
@Component
public class AnonymousLoginStrategy implements LoginStrategy {

    private final MemberRepository memberRepository;
    private final MemberMapper memberMapper;
    private final MemberService memberService;
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

        return memberMapper.toLoginMember(member, tokenInfo, isServiceMember, member.getRole());
    }

    @Override
    public MemberLoginResponse login(MemberLoginRequest request) {
        // todo : MemberLoginRequest  방식은 지원하지 않습니다. RestApiException으로 변경
        throw new UnsupportedOperationException("MemberLoginRequest  방식은 지원하지 않습니다.");
    }

    private MemberLoginResponse saveNewMember(String clientId, LoginType loginType) {
        Member member = memberMapper.toMember(clientId, loginType);
        member.changeRole(Role.GUEST);
        Member newMember = memberService.saveEntity(member);
        TokenInfo tokenInfo = generateToken(newMember);
        return memberMapper.toLoginMember(newMember, tokenInfo, false, Role.GUEST);
    }

    private TokenInfo generateToken(Member member) {
        return jwtProvider.generateToken(member.getId().toString(), member.getRole().toString());
    }
}

