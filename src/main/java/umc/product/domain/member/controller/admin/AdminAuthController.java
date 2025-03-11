package umc.product.domain.member.controller.admin;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import umc.product.domain.member.adviser.admin.AdminAuthAdviser;
import umc.product.domain.member.dto.request.admin.auth.AdminLoginRequest;
import umc.product.domain.member.dto.request.admin.auth.AdminSignUpRequest;
import umc.product.domain.member.dto.response.member.common.MemberIdResponse;
import umc.product.domain.member.dto.response.member.auth.MemberLoginResponse;
import umc.product.global.common.base.BaseResponse;

@Tag(name = "운영진용(Web) Member Auth API", description = "운영진용(Web) Member Auth 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/web/admin/auth")
public class AdminAuthController {
    private final AdminAuthAdviser adminAuthAdviser;

    @Operation(summary = "학교 web 계정 회원가입 API", description = "학교 web 계정을 회원가입하는 API입니다")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "학교 web 계정 회원가입 성공"
            )
    })
    @PostMapping( path = "/signup",consumes = { MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public BaseResponse<MemberIdResponse> signUp(
            @RequestPart AdminSignUpRequest request,
            @RequestPart(name = "avatarImage", required = false)
            @Parameter(description = "사용자 프로필 이미지(선택 사항)", required = false) MultipartFile file
    ) {
        return BaseResponse.onSuccess(adminAuthAdviser.signUp(file, request));
    }

    @Operation(summary = "web 계정 로그인 API", description = "web 계정을 로그인하는 API입니다")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "web 계정 로그인 성공"
            )
    })
    @PostMapping("/login")
    public BaseResponse<MemberLoginResponse> login(
            @RequestBody AdminLoginRequest request
    ) {
        return BaseResponse.onSuccess(adminAuthAdviser.login(request));
    }
}
