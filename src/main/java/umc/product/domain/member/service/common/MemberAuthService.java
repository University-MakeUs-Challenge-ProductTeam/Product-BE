package umc.product.domain.member.service.common;

import umc.product.domain.member.dto.response.member.MemberGenerateTokenResponse;
import umc.product.domain.member.dto.response.member.MemberIdResponse;
import umc.product.domain.member.dto.response.member.MemberLoginResponse;
import umc.product.domain.member.entity.Member;
import umc.product.domain.member.entity.enums.LoginType;
import umc.product.domain.member.entity.enums.Role;
import umc.product.domain.semester.entity.SemesterPart;
import umc.product.domain.semester.entity.SemesterPosition;
import umc.product.domain.university.entity.University;

import java.util.List;

public interface MemberAuthService {
    Member signUp(Member member, University university, List<SemesterPart> semesterPartList, List<SemesterPosition> semesterPositionList, Role role);
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
