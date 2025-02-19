package umc.product.domain.member.adviser.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import umc.product.domain.member.dto.request.common.CommonCodeRequest;
import umc.product.domain.member.dto.request.admin.AdminCodeRequest;
import umc.product.domain.member.dto.response.admin.AdminMemberListResponse;
import umc.product.domain.member.dto.response.common.MemberCodeResponse;
import umc.product.domain.member.dto.response.common.MemberIdResponse;
import umc.product.domain.member.entity.Member;
import umc.product.domain.member.entity.enums.Role;
import umc.product.domain.member.service.admin.AdminMemberService;
import umc.product.domain.member.service.admin.AdminCodeService;
import umc.product.domain.member.service.common.MemberService;
import umc.product.domain.university.entity.University;
import umc.product.domain.university.service.UniversityService;

@Component
@RequiredArgsConstructor
public class AdminMemberAdviser {
    private final AdminCodeService adminCodeService;
    private final AdminMemberService adminMemberService;
    private final MemberService memberService;
    private final UniversityService universityService;

    public MemberCodeResponse createAdminCode(AdminCodeRequest request) {
        String code = adminCodeService.createAdminCode();
        University university = universityService.findOrCreateUniversity(request.getUniversity());
        return adminCodeService.saveAdminCode(request, code);
    }

    public MemberCodeResponse createChallengerCode(CommonCodeRequest request) {
        String code = adminCodeService.createChallengerCode();
        University university = universityService.findOrCreateUniversity(request.getUniversity());
        return adminCodeService.saveChallengerCode(request, code);
    }

    public AdminMemberListResponse searchMembers(Member member, Pageable pageable, String semester, Role role, String part) {
        return adminMemberService.findMembers(member, pageable, semester, role, part);
    }

    public MemberIdResponse outChallenger(Long memberId) {
        Member member = memberService.findById(memberId);
        return adminMemberService.outChallenger(member);
    }
}
