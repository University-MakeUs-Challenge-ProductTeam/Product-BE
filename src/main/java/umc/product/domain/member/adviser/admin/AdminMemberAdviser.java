package umc.product.domain.member.adviser.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import umc.product.domain.member.converter.response.MemberCodeConverter;
import umc.product.domain.member.converter.response.MemberConverter;
import umc.product.domain.member.dto.request.admin.AdminCodeRequest;
import umc.product.domain.member.dto.response.admin.AdminMemberListResponse;
import umc.product.domain.member.dto.response.member.MemberCodeResponse;
import umc.product.domain.member.dto.response.member.MemberIdResponse;
import umc.product.domain.member.entity.Member;
import umc.product.domain.member.entity.enums.Part;
import umc.product.domain.member.entity.enums.Role;
import umc.product.domain.member.service.admin.AdminMemberService;
import umc.product.domain.member.service.admin.AdminCodeService;
import umc.product.domain.member.service.common.MemberService;
import umc.product.domain.university.entity.University;
import umc.product.domain.university.service.UniversityService;

import java.util.List;

@Component
@RequiredArgsConstructor
public class AdminMemberAdviser {
    private final AdminCodeService adminCodeService;
    private final AdminMemberService adminMemberService;
    private final MemberService memberService;
    private final UniversityService universityService;

    private final MemberConverter memberConverter;
    private final MemberCodeConverter memberCodeConverter;

    public MemberCodeResponse createUniversityAdminCode(AdminCodeRequest request) {
        String code = adminCodeService.createAdminCode();
        University university = universityService.findOrCreateUniversity(request.getUniversity());      //학교 생성 or 찾기
        adminCodeService.saveAdminCode(request, code);
        return memberCodeConverter.toMemberCodeResponse(code);
    }

    public MemberCodeResponse createAdminCode(AdminCodeRequest request) {
        String code = adminCodeService.createChallengerCode();
        University university = universityService.findUniversity(request.getUniversity());
        adminCodeService.saveAdminCode(request, code);
        return memberCodeConverter.toMemberCodeResponse(code);
    }

    public MemberCodeResponse createChallengerCode(AdminCodeRequest request) {
        String code = adminCodeService.createChallengerCode();
        University university = universityService.findUniversity(request.getUniversity());
        adminCodeService.saveAdminCode(request, code);
        return memberCodeConverter.toMemberCodeResponse(code);
    }

    public AdminMemberListResponse filterSearchMembers(Member member, Pageable pageable, String semester, Role role, Part part) {
        List<Member> memberList = adminMemberService.findMembers(member, pageable, semester, role, part);
        return memberConverter.toAdminMemberListResponse(memberList);
    }

    public MemberIdResponse outChallenger(Long memberId) {
        Member member = memberService.findById(memberId);
        adminMemberService.outChallenger(member);
        return memberConverter.toMemberIdResponse(member.getId());
    }

    public AdminMemberListResponse searchMembers(String searchString) {
        List<Member> memberList = adminMemberService.findMembersBySearchString(searchString);
        return memberConverter.toAdminMemberListResponse(memberList);
    }
}
