package umc.product.domain.member.controller.member;

import org.springframework.http.MediaType;
import org.springframework.web.multipart.MultipartFile;
import umc.product.domain.member.adviser.member.MemberAuthAdviser;
import umc.product.domain.member.dto.request.member.MemberSignUpRequest;
import umc.product.domain.member.entity.Member;
import umc.product.domain.member.entity.enums.LoginType;
import umc.product.domain.member.dto.response.member.MemberGenerateTokenResponse;
import umc.product.domain.member.dto.response.member.MemberIdResponse;
import umc.product.domain.member.dto.response.member.MemberLoginResponse;
import umc.product.global.common.base.BaseResponse;
import umc.product.global.config.security.auth.CurrentMember;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Tag(name = "인증 API", description = "멤버 인증 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/members/auth")
public class MemberAuthController {
    private final MemberAuthAdviser memberAuthAdviser;

    @Operation(summary = "챌린저 회원가입 API", description = "챌린저 멤버 정보를 등록하는 API입니다")
    @PostMapping(path = "/signup",consumes = { MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public BaseResponse<MemberIdResponse> signUp(@RequestPart MemberSignUpRequest request,
                                                 @RequestPart("file") MultipartFile file) {
        return BaseResponse.onSuccess(memberAuthAdviser.signUp(file, request));
    }

    @Operation(summary = "소셜 로그인 API", description = "네이버, 카카오, 구글 로그인을 수행하는 API입니다. 소셜 로그인은 일반 챌린저 용입니다. (비회원 로그인은 기능에 없으나, 태스트 하기 편하라고 남겨둠니다.)")
    @PostMapping("/social/login")
    public BaseResponse<MemberLoginResponse> socialLogin(@RequestHeader(value = "accessToken") String accessToken,
                                                         @RequestParam(value = "loginType") LoginType loginType) {
        return BaseResponse.onSuccess(memberAuthAdviser.socialLogin(accessToken, loginType));
    }

    @Operation(summary = "accessToken 재발급 API", description = "refreshToken가 유효하다면 새로운 accessToken을 발급하는 API입니다.")
    @GetMapping("/token/refresh")
    public BaseResponse<MemberGenerateTokenResponse> regenerateToken(@RequestHeader(value = "refreshToken") String refreshToken) {
        return BaseResponse.onSuccess(memberAuthAdviser.regenerateToken(refreshToken));
    }

    @Operation(summary = "로그아웃 API", description = "해당 유저의 refreshToken을 삭제하는 API입니다.")
    @DeleteMapping("/logout")
    public BaseResponse<MemberIdResponse> logout(@CurrentMember Member member) {
        return BaseResponse.onSuccess(memberAuthAdviser.logout(member));
    }

    @Operation(summary = "회원 탈퇴 API", description = "해당 유저 정보를 삭제하는 API입니다.")
    @DeleteMapping
    public BaseResponse<MemberIdResponse> withdrawal(@CurrentMember Member member) {
        return BaseResponse.onSuccess(memberAuthAdviser.withdrawal(member));
    }

}

