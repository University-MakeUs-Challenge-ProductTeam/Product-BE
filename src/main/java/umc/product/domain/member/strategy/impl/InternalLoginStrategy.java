package umc.product.domain.member.strategy.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import umc.product.domain.member.dto.request.MemberLoginRequest;
import umc.product.domain.member.dto.response.MemberLoginResponse;
import umc.product.domain.member.entity.LoginType;
import umc.product.domain.member.entity.Member;
import umc.product.domain.member.entity.MemberLoginInfo;
import umc.product.domain.member.entity.Role;
import umc.product.domain.member.mapper.MemberMapper;
import umc.product.domain.member.repository.MemberLoginInfoRepository;
import umc.product.domain.member.repository.MemberRepository;
import umc.product.domain.member.service.MemberService;
import umc.product.domain.member.strategy.LoginStrategy;
import umc.product.global.common.exception.RestApiException;
import umc.product.global.config.security.jwt.JwtProvider;
import umc.product.global.config.security.jwt.TokenInfo;

import java.util.Optional;

import static umc.product.domain.member.status.MemberErrorStatus.AUTHENTICATION_FAILED;
import static umc.product.domain.member.status.MemberErrorStatus.PASSWORD_MISMATCH;

@Component
@RequiredArgsConstructor
public class InternalLoginStrategy implements LoginStrategy {
    private final MemberLoginInfoRepository memberLoginInfoRepository;
    private final MemberMapper memberMapper;
    private final JwtProvider jwtProvider;
    private final PasswordEncoder passwordEncoder; // Spring Security PasswordEncoder 사용

    @Override
    public MemberLoginResponse login(String accessToken) {
        // todo : AccessToken 방식은 지원하지 않습니다. RestApiException으로 변경
        throw new UnsupportedOperationException("AccessToken 방식은 지원하지 않습니다.");
    }

    @Override
    public MemberLoginResponse login(MemberLoginRequest request) {
        // 회원 조회
        MemberLoginInfo memberLoginInfo = memberLoginInfoRepository.findByMemberLoginId(request.getMemberId())
                .orElseThrow(() -> new RestApiException(AUTHENTICATION_FAILED));
        Member member = memberLoginInfo.getMember();

        // 비밀번호 검증
        if (!passwordEncoder.matches(request.getPassword(), member.getMemberLoginInfo().getPassword())) {
            throw new RestApiException(PASSWORD_MISMATCH);
        }

        // JWT 토큰 생성
        TokenInfo tokenInfo = generateToken(member);

        // 응답 객체 반환 (회원가입 완료된 멤버인지 판병)
        boolean isServiceMember = member.getName() != null;

        return memberMapper.toLoginMember(member, tokenInfo, isServiceMember, member.getRole());
    }

    private TokenInfo generateToken(Member member) {
        return jwtProvider.generateToken(member.getId().toString(), member.getRole().toString());
    }
}
