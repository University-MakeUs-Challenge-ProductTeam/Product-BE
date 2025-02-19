package umc.product.domain.member.adviser.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestHeader;
import umc.product.domain.member.dto.request.admin.AdminCodeRequest;
import umc.product.domain.member.dto.request.admin.AdminLoginRequest;
import umc.product.domain.member.dto.request.admin.AdminSignUpRequest;
import umc.product.domain.member.dto.request.challenger.ChallengerCodeRequest;
import umc.product.domain.member.dto.response.admin.AdminMemberListResponse;
import umc.product.domain.member.dto.response.common.MemberCodeResponse;
import umc.product.domain.member.dto.response.common.MemberGenerateTokenResponse;
import umc.product.domain.member.dto.response.common.MemberIdResponse;
import umc.product.domain.member.dto.response.common.MemberLoginResponse;
import umc.product.domain.member.entity.Member;
import umc.product.domain.member.entity.enums.Role;
import umc.product.domain.member.service.MemberAdminService;
import umc.product.domain.member.service.MemberAuthService;
import umc.product.domain.member.service.MemberCodeService;
import umc.product.domain.member.service.MemberService;
import umc.product.global.config.security.auth.CurrentMember;

@Component
@RequiredArgsConstructor
public class AdminAuthAdviser {
    private final MemberAdminService memberAdminService;
    private final MemberAuthService memberAuthService;

    public MemberIdResponse signUp(AdminSignUpRequest request){
        return memberAdminService.signUp(request);
    }

    public MemberLoginResponse login(AdminLoginRequest request) { return memberAuthService.login(request); }

    public MemberGenerateTokenResponse regenerateToken(String refreshToken, Member member) {
        return memberAuthService.generateNewAccessToken(refreshToken, member);
    }

    public MemberIdResponse logout(Member member) {return memberAuthService.logout(member);}

    public MemberIdResponse withdrawal(Member member) {return memberAuthService.withdrawal(member);}
}
