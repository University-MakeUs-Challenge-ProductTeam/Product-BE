package umc.product.domain.member.service.common;

import org.springframework.data.domain.Pageable;
import umc.product.domain.member.dto.request.admin.AdminLoginRequest;
import umc.product.domain.member.dto.request.admin.AdminSignUpRequest;
import umc.product.domain.member.dto.response.admin.AdminMemberListResponse;
import umc.product.domain.member.dto.response.common.MemberGenerateTokenResponse;
import umc.product.domain.member.dto.response.common.MemberIdResponse;
import umc.product.domain.member.dto.response.common.MemberLoginResponse;
import umc.product.domain.member.entity.Member;
import umc.product.domain.member.entity.enums.LoginType;
import umc.product.domain.member.entity.enums.Role;
import umc.product.domain.university.entity.University;

public interface MemberAuthService {
    Member signUp(Member member, University university);
    // 소셜 로그인
    MemberLoginResponse socialLogin(final String accessToken, LoginType loginType);
    // 새로운 액세스 토큰 발급
    MemberGenerateTokenResponse generateNewAccessToken(String refreshToken, Member member);
    // 로그아웃
    MemberIdResponse logout(Member member);
    // 회원 탈퇴
    MemberIdResponse withdrawal(Member member);

    void verifyMemberCode(String code);

}
