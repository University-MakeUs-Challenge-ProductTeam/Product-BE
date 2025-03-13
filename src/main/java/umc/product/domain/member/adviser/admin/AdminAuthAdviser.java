package umc.product.domain.member.adviser.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import umc.product.domain.file.dto.FileCreateResponse;
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

    public MemberIdResponse signUp(
            AdminSignUpRequest request
    ){
        //1차 MVP이후 회원가입 시 프로필 사진 설정 생기면 사용
        /*if(file != null) {
            FileCreateResponse fileCreateResponse = fileService.createFile("avatar", file);
        }*/
        memberAuthService.verifyClientId(request.clientId());
        University university = universityService.findUniversity(request.universityName());
        Member member = adminMemberService.toAdminMember(request, "", university.getName());
        //임시로 기본 프로필 사진으로 지정
        Member newMember = adminAuthService.signUp(request, member, university, "https://umc-offcial-product.s3.ap-northeast-2.amazonaws.com/avatar/default-avatar-img_5182333b-1626-4ddf-b5ab-646c916253cf.jpg");
        return memberConverter.toMemberIdResponse(newMember.getId());
    }

    public MemberLoginResponse login(
            AdminLoginRequest request
    ) {
        return adminAuthService.login(request);
    }
}
