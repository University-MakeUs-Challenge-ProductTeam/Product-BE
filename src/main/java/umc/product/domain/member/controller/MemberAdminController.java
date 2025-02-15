package umc.product.domain.member.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import umc.product.domain.member.dto.request.MemberCodeRequest;
import umc.product.domain.member.dto.request.MemberSignUpRequest;
import umc.product.domain.member.dto.response.MemberCodeResponse;
import umc.product.domain.member.dto.response.MemberIdResponse;
import umc.product.domain.member.service.MemberAdminService;
import umc.product.domain.member.service.MemberCodeService;
import umc.product.domain.member.service.MemberService;
import umc.product.global.common.base.BaseResponse;

@Tag(name = "Admin API", description = "Admin 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/members/admin")
public class MemberAdminController {
    private final MemberService memberService;
    private final MemberAdminService memberAdminService;

    @Operation(summary = "ADMIN 회원가입 API", description = "최초 ADMIN 멤버 정보를 등록하는 API입니다")
    @ApiResponses( value = {
            @ApiResponse(responseCode = "COMMON200", description = "성공"),
            @ApiResponse(responseCode = "UNIVERSITY001", description = "대학교명을 잘못 입력하였을 경우 발생"),
            @ApiResponse(responseCode = "BRANCH001", description = "대학교가 지부랑 연결되어 있지 않을 경우 발생")
    })
    @PostMapping("/signup")
    public BaseResponse<MemberIdResponse> signUp(@Valid @RequestBody MemberSignUpRequest request) {
        return BaseResponse.onSuccess(memberService.signUp(request));
    }
    @Operation(summary = "챌린저 확인코드 발급 API", description = "챌린저 확인코드 발급하는 API입니다")
    @ApiResponses( value = {
            @ApiResponse(responseCode = "COMMON200", description = "성공"),
            @ApiResponse(responseCode = "UNIVERSITY001", description = "대학교명을 잘못 입력하였을 경우 발생"),
            @ApiResponse(responseCode = "BRANCH001", description = "대학교가 지부랑 연결되어 있지 않을 경우 발생")
    })
    @PostMapping("/generate/code")
    public BaseResponse<MemberCodeResponse> generateCode(@Valid @RequestBody MemberCodeRequest request) {
        return BaseResponse.onSuccess(memberAdminService.generateCode(request));
    }
}
