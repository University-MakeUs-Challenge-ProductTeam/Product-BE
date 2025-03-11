package umc.product.domain.member.adviser.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import umc.product.domain.member.converter.response.MemberCodeConverter;
import umc.product.domain.member.dto.response.admin.code.AdminCreateCodeResponse;
import umc.product.domain.member.dto.response.admin.code.AdminVerifyCodeResponse;
import umc.product.domain.member.dto.response.admin.register.AdminRegisterListResponse;
import umc.product.domain.member.entity.Member;
import umc.product.domain.member.entity.enums.Role;
import umc.product.domain.member.entity.enums.Status;
import umc.product.domain.member.service.admin.AdminCodeService;
import umc.product.domain.member.service.member.MemberService;
import umc.product.domain.university.service.UniversityService;
import umc.product.global.common.exception.RestApiException;

import java.util.Map;

import static umc.product.domain.member.status.AuthErrorStatus.INVALID_ROLE;
import static umc.product.domain.member.status.MemberErrorStatus.INVALID_MEMBER_STATUS;

@Component
@RequiredArgsConstructor
public class AdminCodeAdviser {
    private final AdminCodeService adminCodeService;
    private final MemberService memberService;

    private final UniversityService universityService;
    private final MemberCodeConverter memberCodeConverter;

    public AdminVerifyCodeResponse verifyMemberCode(
            String code
    ) {
        String universityName = adminCodeService.verifyWebAdminCode(code);
        return memberCodeConverter.toAdminCodeVerifyResponse(universityName);
    }

    public AdminCreateCodeResponse createWebAdminCode(
            Member member,
            String universityName
    ) {
        if(member.getRole().getPriority() > Role.CENTRAL_ADMIN.getPriority()) throw new RestApiException(INVALID_ROLE);
        universityService.findOrCreateUniversity(universityName);      //학교 생성 or 찾기
        String code = adminCodeService.createWebAdminCode();
        adminCodeService.saveWebAdminCode(universityName, code);
        return memberCodeConverter.toAdminCodeResponse(code);
    }

    public AdminRegisterListResponse createIndividualAppCode(
            Member member,
            Long memberId
    ) {
        Member targetMember = memberService.findByIdNotFetchLoginInfo(memberId);
        //등록된 사용자가 맞는지 체크
        if(targetMember.getStatus() != Status.WAITING_FOR_UPDATE) throw new RestApiException(INVALID_MEMBER_STATUS);
        //더 상위 권한의 유저의 코드는 발급할 수 없음
        if(targetMember.getRole().getPriority() < member.getRole().getPriority()) throw new RestApiException(INVALID_ROLE);
        Map<String, Member> codeMap = adminCodeService.createIndividualAppCode(targetMember);
        adminCodeService.saveAppCode(codeMap);
        return memberCodeConverter.toMemberCodeResponse(codeMap);
    }
}
