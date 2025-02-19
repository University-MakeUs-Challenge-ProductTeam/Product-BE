package umc.product.domain.member.adviser.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import umc.product.domain.file.service.FileService;
import umc.product.domain.member.dto.request.admin.AdminLoginRequest;
import umc.product.domain.member.dto.request.admin.AdminSignUpRequest;
import umc.product.domain.member.dto.response.common.MemberGenerateTokenResponse;
import umc.product.domain.member.dto.response.common.MemberIdResponse;
import umc.product.domain.member.dto.response.common.MemberLoginResponse;
import umc.product.domain.member.entity.Member;
import umc.product.domain.member.entity.MemberLoginInfo;
import umc.product.domain.member.mapper.MemberInfoMapper;
import umc.product.domain.member.mapper.MemberMapper;
import umc.product.domain.member.service.admin.AdminAuthService;
import umc.product.domain.member.service.common.MemberAuthService;
import umc.product.domain.university.entity.University;
import umc.product.domain.university.repository.UniversityRepository;
import umc.product.domain.university.service.UniversityService;

@Component
@RequiredArgsConstructor
public class AdminAuthAdviser {
    private final AdminAuthService adminAuthService;
    private final MemberAuthService memberAuthService;
    private final UniversityService universityService;
    private final FileService fileService;

    private final MemberMapper memberMapper;

    public MemberIdResponse signUp(MultipartFile file, AdminSignUpRequest request){
        //FileCreateResponse fileCreateResponse = fileService.createFile("AVATAR-IMAGE", file);
        University university = universityService.findOrCreateUniversity(request.getUniversity());
        Member member = memberMapper.toAdminMember(request, "");
        Member newMember = adminAuthService.signUp(member, request.getPassword(), university);
        return memberMapper.toMemberIdResponse(newMember.getId());
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
