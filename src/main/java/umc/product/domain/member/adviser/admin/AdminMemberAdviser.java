package umc.product.domain.member.adviser.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import umc.product.domain.member.dto.request.common.CommonCodeRequest;
import umc.product.domain.member.dto.request.admin.AdminCodeRequest;
import umc.product.domain.member.dto.response.admin.AdminMemberListResponse;
import umc.product.domain.member.dto.response.common.MemberCodeResponse;
import umc.product.domain.member.dto.response.common.MemberIdResponse;
import umc.product.domain.member.dto.response.common.MemberSearchResponse;
import umc.product.domain.member.entity.Member;
import umc.product.domain.member.entity.enums.Role;
import umc.product.domain.member.mapper.MemberCodeMapper;
import umc.product.domain.member.mapper.MemberMapper;
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

    private final MemberMapper memberMapper;
    private final MemberCodeMapper memberCodeMapper;

    public MemberCodeResponse createAdminCode(AdminCodeRequest request) {
        String code = adminCodeService.createAdminCode();
        University university = universityService.findOrCreateUniversity(request.getUniversity());
        adminCodeService.saveAdminCode(request, code);
        return memberCodeMapper.toMemberCodeResponse(code);
    }

    public MemberCodeResponse createChallengerCode(CommonCodeRequest request) {
        String code = adminCodeService.createChallengerCode();
        University university = universityService.findOrCreateUniversity(request.getUniversity());
        adminCodeService.saveChallengerCode(request, code);
        return memberCodeMapper.toMemberCodeResponse(code);
    }

    public AdminMemberListResponse filterSearchMembers(Member member, Pageable pageable, String semester, Role role, String part) {
        List<Member> memberList = adminMemberService.findMembers(member, pageable, semester, role, part);
        return memberMapper.toAdminMemberListResponse(memberList);
    }

    public MemberIdResponse outChallenger(Long memberId) {
        Member member = memberService.findById(memberId);
        adminMemberService.outChallenger(member);
        return memberMapper.toMemberIdResponse(member.getId());
    }

    public AdminMemberListResponse searchMembers(String searchString) {
        List<Member> memberList = adminMemberService.findMembersBySearchString(searchString);
        return memberMapper.toAdminMemberListResponse(memberList);
    }
}
