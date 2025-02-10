package umc.product.domain.member.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import umc.product.domain.member.dto.request.MemberLoginRequest;
import umc.product.domain.member.dto.request.MemberSignUpRequest;
import umc.product.domain.member.dto.response.MemberGenerateTokenResponse;
import umc.product.domain.member.dto.response.MemberIdResponse;
import umc.product.domain.member.dto.response.MemberLoginResponse;
import umc.product.domain.member.entity.LoginType;
import umc.product.domain.member.entity.Member;
import umc.product.domain.member.service.MemberAuthService;
import umc.product.global.common.base.BaseResponse;
import umc.product.global.config.security.auth.CurrentMember;

@Tag(name = "멤버 API", description = "멤버 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberController {

    // todo: MemberService로 옮기기
    private final MemberAuthService memberAuthService;


    // todo : 챌린저 회원가입, 새로운 학교 회원가입 나누기 + 초대 코드 로직 추가 후 개발
    @Operation(summary = "회원가입 API", description = "최초 멤버 정보를 등록하는 API입니다. 챌린저 회원가입 플로우 구현은 아직 미완성 입니다.")
    @ApiResponses( value = {
            @ApiResponse(responseCode = "COMMON200", description = "성공"),
            @ApiResponse(responseCode = "UNIVERSITY001", description = "대학교명을 잘못 입력하였을 경우 발생"),
            @ApiResponse(responseCode = "BRANCH001", description = "대학교가 지부랑 연결되어 있지 않을 경우 발생")
    })
    @PostMapping
    public BaseResponse<MemberIdResponse> signUp(@CurrentMember Member member,
                                                 @Valid @RequestBody MemberSignUpRequest request) {
        return BaseResponse.onSuccess(memberAuthService.signUp(member, request));
    }


}
