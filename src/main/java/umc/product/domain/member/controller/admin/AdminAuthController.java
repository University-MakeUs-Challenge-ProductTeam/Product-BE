package umc.product.domain.member.controller.admin;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import umc.product.domain.member.adviser.admin.AdminAuthAdviser;
import umc.product.domain.member.dto.request.admin.AdminLoginRequest;
import umc.product.domain.member.dto.request.admin.AdminSignUpRequest;
import umc.product.domain.member.dto.response.member.MemberGenerateTokenResponse;
import umc.product.domain.member.dto.response.member.MemberIdResponse;
import umc.product.domain.member.dto.response.member.MemberLoginResponse;
import umc.product.domain.member.entity.Member;
import umc.product.global.common.base.BaseResponse;
import umc.product.global.config.security.auth.CurrentMember;

@Tag(name = "어드민 Auth 공통로직 API", description = "어드민(중앙, 중앙 운영진, 학교)Auth 공통 로직 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/web/admin/auth")
public class AdminAuthController {
    private final AdminAuthAdviser adminAuthAdviser;

    @Operation(summary = "ADMIN 회원가입 API", description = "최초 ADMIN 멤버 정보를 등록하는 API입니다")
    @PostMapping( path = "/signup",consumes = { MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public BaseResponse<MemberIdResponse> signUp(@RequestPart AdminSignUpRequest request,
                                                 @RequestPart("file") MultipartFile file) {
        return BaseResponse.onSuccess(adminAuthAdviser.signUp(file, request));
    }

    @PostMapping("/login")
    public BaseResponse<MemberLoginResponse> login(@RequestBody AdminLoginRequest request) {
        return BaseResponse.onSuccess(adminAuthAdviser.login(request));
    }

    @Operation(summary = "로그아웃 API", description = "해당 유저의 refreshToken을 삭제하는 API입니다.")
    @DeleteMapping("/logout")
    public BaseResponse<MemberIdResponse> logout(@CurrentMember Member member) {
        return BaseResponse.onSuccess(adminAuthAdviser.logout(member));
    }

    @Operation(summary = "회원 탈퇴 API", description = "해당 유저 정보를 삭제하는 API입니다.")
    @ApiResponses( value = {
            @ApiResponse(responseCode = "COMMON200", description = "성공")
    })
    @DeleteMapping
    public BaseResponse<MemberIdResponse> withdrawal(@CurrentMember Member member) {
        return BaseResponse.onSuccess(adminAuthAdviser.withdrawal(member));
    }
}
