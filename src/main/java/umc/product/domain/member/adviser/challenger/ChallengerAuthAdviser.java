package umc.product.domain.member.adviser.challenger;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;
import umc.product.domain.member.dto.response.common.MemberGenerateTokenResponse;
import umc.product.domain.member.dto.response.common.MemberIdResponse;
import umc.product.domain.member.dto.response.common.MemberLoginResponse;
import umc.product.domain.member.dto.response.common.MemberRoleResponse;
import umc.product.domain.member.entity.Member;
import umc.product.domain.member.entity.enums.LoginType;
import umc.product.domain.member.service.MemberAuthService;
import umc.product.domain.member.service.MemberCodeService;

@Component
@RequiredArgsConstructor
public class ChallengerAuthAdviser {
    private final MemberAuthService memberAuthService;

    public MemberLoginResponse socialLogin(String accessToken, LoginType loginType) { return memberAuthService.socialLogin(accessToken, loginType);}

    public MemberGenerateTokenResponse regenerateToken(String refreshToken, Member member) {
        return memberAuthService.generateNewAccessToken(refreshToken, member);
    }

    public MemberIdResponse logout(Member member) {return memberAuthService.logout(member);}

    public MemberIdResponse withdrawal(Member member) {return memberAuthService.withdrawal(member);}


}
