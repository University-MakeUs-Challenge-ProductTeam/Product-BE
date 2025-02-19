package umc.product.domain.member.adviser;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import umc.product.domain.member.dto.request.admin.AdminSignUpRequest;
import umc.product.domain.member.dto.request.challenger.ChallengerCodeRequest;
import umc.product.domain.member.dto.request.admin.AdminCodeRequest;
import umc.product.domain.member.dto.response.admin.AdminMemberListResponse;
import umc.product.domain.member.dto.response.common.MemberIdResponse;
import umc.product.domain.member.dto.response.common.MemberCodeResponse;
import umc.product.domain.member.dto.response.common.MemberSearchResponse;
import umc.product.domain.member.entity.Member;
import umc.product.domain.member.entity.enums.Role;
import umc.product.domain.member.service.MemberAdminService;
import umc.product.domain.member.service.MemberCodeService;
import umc.product.domain.member.service.MemberService;

import java.util.List;

@Component
@RequiredArgsConstructor
public class MemberAdminAdviser {
    private final MemberCodeService memberCodeService;
    private final MemberAdminService memberAdminService;
    private final MemberService memberService;

    public MemberIdResponse signUp(AdminSignUpRequest request){
        return memberAdminService.signUp(request);
    }

    public MemberCodeResponse createAdminCode(AdminCodeRequest request) {
        String code = memberCodeService.createAdminCode();
        return memberCodeService.saveAdminCode(request, code);
    }

    public MemberCodeResponse createChallengerCode(ChallengerCodeRequest request) {
        String code = memberCodeService.createChallengerCode();
        return memberCodeService.saveChallengerCode(request, code);
    }

    public AdminMemberListResponse searchMembers(Member member, Pageable pageable, String semester, Role role, String part) {
        return memberService.findMembers(member, pageable, semester, role, part);
    }
}
