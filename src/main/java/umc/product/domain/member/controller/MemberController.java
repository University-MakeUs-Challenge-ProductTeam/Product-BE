package umc.product.domain.member.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import umc.product.domain.member.dto.request.MemberSignUpRequest;
import umc.product.domain.member.dto.response.MemberIdResponse;
import umc.product.domain.member.entity.Member;
import umc.product.domain.member.service.MemberAuthService;
import umc.product.domain.member.service.MemberService;
import umc.product.global.common.base.BaseResponse;
import umc.product.global.config.security.auth.CurrentMember;

@Tag(name = "멤버 API", description = "멤버 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberController {
    private final MemberService memberService;

    @Operation(summary = "ADMIN 회원가입 API", description = "최초 ADMIN 멤버 정보를 등록하는 API입니다")
    @ApiResponses( value = {
            @ApiResponse(responseCode = "COMMON200", description = "성공"),
            @ApiResponse(responseCode = "UNIVERSITY001", description = "대학교명을 잘못 입력하였을 경우 발생"),
            @ApiResponse(responseCode = "BRANCH001", description = "대학교가 지부랑 연결되어 있지 않을 경우 발생")
    })
    @PostMapping("/signup/admin")
    public BaseResponse<MemberIdResponse> signUp(@Valid @RequestBody MemberSignUpRequest request) {
        return BaseResponse.onSuccess(memberService.signUp(request));
    }
}
