package umc.product.domain.member.adviser.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import umc.product.domain.file.service.FileService;
import umc.product.domain.member.dto.request.admin.AdminLoginRequest;
import umc.product.domain.member.dto.request.admin.AdminSignUpRequest;
import umc.product.domain.member.dto.response.common.MemberGenerateTokenResponse;
import umc.product.domain.member.dto.response.common.MemberIdResponse;
import umc.product.domain.member.dto.response.common.MemberLoginResponse;
import umc.product.domain.member.entity.Member;
import umc.product.domain.member.service.admin.AdminAuthService;
import umc.product.domain.member.service.common.MemberAuthService;

@Component
@RequiredArgsConstructor
public class AdminAuthAdviser {
    private final AdminAuthService adminAuthService;
    private final MemberAuthService memberAuthService;
    private final FileService fileService;

    public MemberIdResponse signUp(MultipartFile file, AdminSignUpRequest request){
        //FileCreateResponse fileCreateResponse = fileService.createFile("AVATAR-IMAGE", file);
        return adminAuthService.signUp(request, "");
    }

    public MemberLoginResponse login(AdminLoginRequest request) { return memberAuthService.login(request); }

    public MemberGenerateTokenResponse regenerateToken(String refreshToken, Member member) {
        return memberAuthService.generateNewAccessToken(refreshToken, member);
    }

    public MemberIdResponse logout(Member member) {return memberAuthService.logout(member);}

    public MemberIdResponse withdrawal(Member member) {return memberAuthService.withdrawal(member);}
}
