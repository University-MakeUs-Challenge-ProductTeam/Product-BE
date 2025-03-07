package umc.product.domain.member.controller.admin;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import umc.product.domain.member.adviser.admin.AdminCodeAdviser;
import umc.product.domain.member.dto.response.admin.code.AdminCreateCodeResponse;
import umc.product.domain.member.dto.response.admin.code.AdminVerifyCodeResponse;
import umc.product.domain.member.dto.request.admin.code.AdminCreateCodeListResponse;
import umc.product.domain.member.entity.Member;
import umc.product.global.common.base.BaseResponse;
import umc.product.global.config.security.auth.CurrentMember;

@Tag(name = "어드민 Member Code 공통 로직 API", description = "Admin Member Code 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/web/admin/members")
public class AdminCodeController {
    private final AdminCodeAdviser adminCodeAdviser;

    @Operation(summary = "UMC 코드 인증 API(학교계정)")
    @GetMapping("/code/verify")
    public BaseResponse<AdminVerifyCodeResponse> verifyMemberCode(@RequestParam String code) {
        return BaseResponse.onSuccess(adminCodeAdviser.verifyMemberCode(code));
    }

    @Operation(summary = "관리자 페이지용 학교코드(학교계정) 발급 API")
    @PostMapping("/create/university-code")
    public BaseResponse<AdminCreateCodeResponse> createWebAdminCode(@CurrentMember Member member,
                                                                    @RequestParam String universityName) {
        return BaseResponse.onSuccess(adminCodeAdviser.createWebAdminCode(null, universityName));
    }

    @Operation(summary = "앱용 개별 확인코드 발급 API", description = "앱에 신규가입, 기존 회원들의 정보를 담은 코드를 개별 발급할 수 있는 API 입니다.")
    @PostMapping("/create/code/{memberId}")
    public BaseResponse<AdminCreateCodeListResponse> createIndividualAppCode(@CurrentMember Member member,
                                                                             @Valid @PathVariable(name = "memberId") Long memberId) {
        return BaseResponse.onSuccess(adminCodeAdviser.createIndividualAppCode(member, memberId));
    }
}
