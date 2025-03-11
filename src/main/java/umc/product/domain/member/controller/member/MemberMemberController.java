package umc.product.domain.member.controller.member;

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
import umc.product.domain.member.adviser.member.MemberMemberAdviser;
import umc.product.domain.member.dto.response.member.code.MemberCodeVerifyResponse;
import umc.product.domain.member.dto.response.member.common.MemberIdResponse;
import umc.product.domain.member.dto.response.member.search.MemberProfileDetailResponse;
import umc.product.domain.member.entity.Member;
import umc.product.global.common.base.BaseResponse;
import umc.product.global.config.security.auth.CurrentMember;

@Tag(name = "일반 사용자(App)용 Member API", description = "일반 사용자(App)용 Member 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberMemberController {
    private final MemberMemberAdviser memberMemberAdviser;

    @Operation(summary = "app UMC 코드 인증 API", description = "발급받은 UMC 코드를 app에서 인증하는 API 입니다. 결과값을 회원가입시에 넣어주세요.")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "UMC 코드 인증 성공"
            )
    })
    @Parameters({
            @Parameter(name = "code", description = "발급받은 UMC 인증 코드"),
    })
    @GetMapping("/code/verify")
    public BaseResponse<MemberCodeVerifyResponse> verifyMemberCode(
            @RequestParam String code
    ) {
        return BaseResponse.onSuccess(memberMemberAdviser.verifyAppCode(code));
    }

    @Operation(summary = "사용자 프로필 사진 변경 API", description = "사용자의 사진 변경하는 API 입니다")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "사용자 프로필 사진 변경 성공"
            )
    })
    @PatchMapping(path = "/profile", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public BaseResponse<MemberIdResponse> modifyMyProfileAvatar(
            @CurrentMember Member member,
            @RequestPart(name = "avatarImage")
            @Parameter(description = "사용자 프로필 이미지(선택 사항)") MultipartFile file
    ) {
        return BaseResponse.onSuccess(memberMemberAdviser.modifyMyProfileAvatar(member, file));
    }

    @Operation(summary = "사용자 프로필 세부사항 조회 API", description = "사용자의 Id로 프로필의 세부사항을 조회하는 API 입니다")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "사용자 프로필 조회 성공"
            )
    })
    @Parameters({
            @Parameter(name = "memberId", description = "프로필 세부사항을 조회하고 싶은 사용자의 Id"),
    })
    @GetMapping("/profile/detail/{memberId}")
    public BaseResponse<MemberProfileDetailResponse> getProfileDetail(
            @PathVariable(name = "memberId") Long memberId) {
        return BaseResponse.onSuccess(memberMemberAdviser.getProfileDetail(memberId));
    }
}
