package umc.product.domain.member.adviser.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import umc.product.domain.file.service.FileService;
import umc.product.domain.member.converter.response.MemberConverter;
import umc.product.domain.member.dto.request.admin.AdminLoginRequest;
import umc.product.domain.member.dto.request.admin.AdminSignUpRequest;
import umc.product.domain.member.dto.response.member.MemberGenerateTokenResponse;
import umc.product.domain.member.dto.response.member.MemberIdResponse;
import umc.product.domain.member.dto.response.member.MemberLoginResponse;
import umc.product.domain.member.entity.Member;
import umc.product.domain.member.service.admin.AdminAuthService;
import umc.product.domain.member.service.admin.AdminMemberService;
import umc.product.domain.member.service.common.MemberAuthService;
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
        University university = universityService.findUniversity(request.getUniversity());
        Member member = adminMemberService.toAdminMember(request, "");
        Member newMember = adminAuthService.signUp(member, request.getPassword(), university);
        return memberConverter.toMemberIdResponse(newMember.getId());
    }

    public MemberLoginResponse login(AdminLoginRequest request) {
        return adminAuthService.login(request);
    }

    public MemberGenerateTokenResponse regenerateToken(String refreshToken, Member member) {
        return memberAuthService.generateNewAccessToken(refreshToken, member);
    }

    public MemberIdResponse logout(Member member) {return memberAuthService.logout(member);}

    public MemberIdResponse withdrawal(Member member) {return memberAuthService.withdrawal(member);}
}
