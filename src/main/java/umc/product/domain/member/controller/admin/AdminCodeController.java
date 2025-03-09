package umc.product.domain.member.controller.admin;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import umc.product.domain.member.adviser.admin.AdminCodeAdviser;
import umc.product.domain.member.dto.response.admin.code.AdminCreateCodeResponse;
import umc.product.domain.member.dto.response.admin.code.AdminVerifyCodeResponse;
import umc.product.domain.member.dto.response.admin.register.AdminRegisterListResponse;
import umc.product.domain.member.entity.Member;
import umc.product.global.common.base.BaseResponse;
import umc.product.global.config.security.auth.CurrentMember;

@Tag(name = "운영진용(Web) Member Code API", description = "운영진용(Web) Member Code 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/web/admin/members")
public class AdminCodeController {
    private final AdminCodeAdviser adminCodeAdviser;

    @Operation(summary = "web UMC 코드 인증 API(학교계정)", description = "web 학교 계정의 UMC 코드를 인증하는 API입니다.")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "web 학교계정 UMC 코드 인증 성공"
            )
    })
    @Parameters({
            @Parameter(name = "code", description = "발급받은 UMC 인증 코드")
    })
    @GetMapping("/code/verify")
    public BaseResponse<AdminVerifyCodeResponse> verifyMemberCode(
            @RequestParam String code
    ) {
        return BaseResponse.onSuccess(adminCodeAdviser.verifyMemberCode(code));
    }

    @Operation(summary = "web 관리자 페이지용 학교 코드(학교계정) 발급 API", description = "web 학교 계정의 UMC 인증 코드를 발급하는 API 입니다")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "web 학교계정 UMC 코드 발급 성공"
            )
    })
    @Parameters({
            @Parameter(name = "universityName", description = "학교 이름(학교가 없다면 생성됩니다)")
    })
    @PostMapping("/create/university-code")
    public BaseResponse<AdminCreateCodeResponse> createWebAdminCode(
            @RequestParam String universityName
    ) {
        return BaseResponse.onSuccess(adminCodeAdviser.createWebAdminCode(null, universityName));
    }

    @Operation(summary = "앱용 개별 확인코드 발급 API", description = "앱에 신규가입, 기존 회원들의 정보를 담은 코드를 개별 발급할 수 있는 API 입니다.")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "app전용 UMC 코드 발급 성공"
            )
    })
    @Parameters({
            @Parameter(name = "memberId", description = "개별적으로 코드를 발급받고 싶은 member의 id")
    })
    @PostMapping("/create/code/{memberId}")
    public BaseResponse<AdminRegisterListResponse> createIndividualAppCode(
            @CurrentMember Member member,
            @Valid @PathVariable(name = "memberId") Long memberId
    ) {
        return BaseResponse.onSuccess(adminCodeAdviser.createIndividualAppCode(member, memberId));
    }
}
