package umc.product.domain.member.adviser.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import umc.product.domain.member.dto.request.challenger.ChallengerCodeRequest;
import umc.product.domain.member.dto.request.admin.AdminCodeRequest;
import umc.product.domain.member.dto.response.admin.AdminMemberListResponse;
import umc.product.domain.member.dto.response.common.MemberCodeResponse;
import umc.product.domain.member.entity.Member;
import umc.product.domain.member.entity.enums.Role;
import umc.product.domain.member.service.MemberCodeService;
import umc.product.domain.member.service.MemberService;

@Component
@RequiredArgsConstructor
public class AdminMemberAdviser {
    private final MemberCodeService memberCodeService;
    private final MemberService memberService;

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
