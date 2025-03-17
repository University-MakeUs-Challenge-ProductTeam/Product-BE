package umc.product.domain.member.service.member;

import umc.product.domain.member.dto.response.member.auth.MemberCreateTokenResponse;
import umc.product.domain.member.dto.response.member.common.MemberIdResponse;
import umc.product.domain.member.dto.response.member.auth.MemberLoginResponse;
import umc.product.domain.member.entity.Member;
import umc.product.domain.member.entity.enums.LoginType;
import umc.product.domain.semester.entity.SemesterPart;
import umc.product.domain.semester.entity.SemesterPosition;

import java.util.List;

public interface MemberAuthService {
    Member signUp(String clientId,
                  Member member,
                  List<SemesterPart> semesterPartList,
                  String avatarUrl);
    // 소셜 로그인
    MemberLoginResponse socialLogin(final String accessToken,
                                    LoginType loginType);
    // 새로운 액세스 토큰 발급
    MemberCreateTokenResponse generateNewAccessToken(String refreshToken,
                                                     Member member);
    // 로그아웃
    MemberIdResponse logout(Member member);
    // 회원 탈퇴
    MemberIdResponse withdrawal(Member member);

    void verifyClientId(String clientId);

}
