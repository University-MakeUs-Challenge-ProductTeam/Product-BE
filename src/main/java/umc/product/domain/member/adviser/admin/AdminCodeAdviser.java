package umc.product.domain.member.adviser.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import umc.product.domain.member.converter.response.MemberCodeConverter;
import umc.product.domain.member.dto.response.admin.code.AdminCreateCodeResponse;
import umc.product.domain.member.dto.response.admin.code.AdminVerifyCodeResponse;
import umc.product.domain.member.dto.response.member.code.MemberCreateCodeListResponse;
import umc.product.domain.member.entity.Member;
import umc.product.domain.member.entity.enums.Status;
import umc.product.domain.member.service.admin.AdminCodeService;
import umc.product.domain.member.service.member.MemberService;
import umc.product.domain.university.entity.University;
import umc.product.domain.university.service.UniversityService;
import umc.product.global.common.exception.RestApiException;

import java.util.List;
import java.util.Map;

import static umc.product.domain.member.status.MemberErrorStatus.NOT_VALID_MEMBER_STATUS;
import static umc.product.global.common.exception.code.status.AuthErrorStatus.INVALID_ROLE;

@Component
@RequiredArgsConstructor
public class AdminCodeAdviser {
    private final AdminCodeService adminCodeService;
    private final MemberService memberService;

    private final UniversityService universityService;
    private final MemberCodeConverter memberCodeConverter;

    public AdminVerifyCodeResponse verifyMemberCode(String code) {
        String universityName = adminCodeService.verifyWebAdminCode(code);
        return memberCodeConverter.toAdminCodeVerifyResponse(universityName);
    }

    public AdminCreateCodeResponse createWebAdminCode(Member member, String universityName) {
        //if(member.getRole().getPriority() > Role.CENTRAL_ADMIN.getPriority()) throw new RestApiException(INVALID_ROLE);
        universityService.findOrCreateUniversity(universityName);      //학교 생성 or 찾기
        String code = adminCodeService.createWebAdminCode();
        adminCodeService.saveWebAdminCode(universityName, code);
        return memberCodeConverter.toAdminCodeResponse(code);
    }

    public MemberCreateCodeListResponse createAppCode(Member member, String universityName) {
        University university = universityService.findUniversity(universityName);
        List<Member> waitingMember = memberService.findWaitingMemberByUniversity(university);

        Map<String, Member> codeMap = adminCodeService.createAppCode(waitingMember);
        adminCodeService.saveAppCode(codeMap);
        return memberCodeConverter.toMemberCodeResponse(codeMap);
    }

    public MemberCreateCodeListResponse createIndividualAppCode(Member member, Long memberId) {
        Member targetMember = memberService.findById(memberId);
        if(targetMember.getStatus() != Status.WAITING_FOR_UPDATE) throw new RestApiException(NOT_VALID_MEMBER_STATUS);
        if(targetMember.getRole().getPriority() < member.getRole().getPriority()) throw new RestApiException(INVALID_ROLE);
        Map<String, Member> codeMap = adminCodeService.createIndividualAppCode(targetMember);
        adminCodeService.saveAppCode(codeMap);
        return memberCodeConverter.toMemberCodeResponse(codeMap);
    }
}
