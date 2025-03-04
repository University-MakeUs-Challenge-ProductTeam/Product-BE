package umc.product.domain.member.adviser.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import umc.product.domain.file.service.FileService;
import umc.product.domain.member.converter.response.MemberConverter;
import umc.product.domain.member.dto.request.admin.auth.AdminLoginRequest;
import umc.product.domain.member.dto.request.admin.auth.AdminSignUpRequest;
import umc.product.domain.member.dto.response.member.common.MemberIdResponse;
import umc.product.domain.member.dto.response.member.auth.MemberLoginResponse;
import umc.product.domain.member.entity.Member;
import umc.product.domain.member.service.admin.AdminAuthService;
import umc.product.domain.member.service.admin.AdminMemberService;
import umc.product.domain.member.service.member.MemberAuthService;
import umc.product.domain.university.entity.University;
import umc.product.domain.university.service.UniversityService;

@Component
@RequiredArgsConstructor
public class AdminAuthAdviser {
    private final AdminAuthService adminAuthService;
    private final AdminMemberService adminMemberService;
    private final MemberAuthService memberAuthService;
    private final UniversityService universityService;
    private final FileService fileService;

    private final MemberConverter memberConverter;

    public MemberIdResponse signUp(MultipartFile file, AdminSignUpRequest request){
        //FileCreateResponse fileCreateResponse = fileService.createFile("AVATAR-IMAGE", file);
        memberAuthService.verifyClientId(request.clientId());
        University university = universityService.findUniversity(request.universityName());
        Member member = adminMemberService.toAdminMember(request, "", university.getName());
        Member newMember = adminAuthService.signUp(member, request.password(), university);
        return memberConverter.toMemberIdResponse(newMember.getId());
    }

    public MemberLoginResponse login(AdminLoginRequest request) {
        return adminAuthService.login(request);
    }
}
