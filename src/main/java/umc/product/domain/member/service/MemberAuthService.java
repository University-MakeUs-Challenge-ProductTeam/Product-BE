package umc.product.domain.member.service;

import umc.product.domain.member.entity.Member;
import umc.product.domain.member.entity.LoginType;
import umc.product.domain.member.dto.request.MemberSignUpRequest;
import umc.product.domain.member.dto.response.MemberGenerateTokenResponse;
import umc.product.domain.member.dto.response.MemberIdResponse;
import umc.product.domain.member.dto.response.MemberLoginResponse;

public interface MemberAuthService {
    // 소셜 로그인
    MemberLoginResponse socialLogin(final String accessToken, LoginType loginType);
    // 회원가입
    MemberIdResponse signUp(Member member, MemberSignUpRequest request);
    // 새로운 액세스 토큰 발급
    MemberGenerateTokenResponse generateNewAccessToken(String refreshToken, Member member);
    // 로그아웃
    MemberIdResponse logout(Member member);
    // 회원 탈퇴
    MemberIdResponse withdrawal(Member member);

}
