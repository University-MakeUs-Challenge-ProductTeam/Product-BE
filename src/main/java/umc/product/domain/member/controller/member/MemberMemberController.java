package umc.product.domain.member.controller.member;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import umc.product.domain.member.adviser.member.MemberMemberAdviser;
import umc.product.domain.member.dto.response.member.code.MemberCodeVerifyResponse;
import umc.product.domain.member.dto.response.member.common.MemberIdResponse;
import umc.product.domain.member.dto.response.member.search.MemberSearchResponse;
import umc.product.domain.member.entity.Member;
import umc.product.global.common.base.BaseResponse;
import umc.product.global.config.security.auth.CurrentMember;

@Tag(name = "멤버 API", description = "멤버 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberMemberController {
    private final MemberMemberAdviser memberMemberAdviser;

    @Operation(summary = "UMC 코드 인증 API", description = "발급받은 UMC 코드를 인증하는 API 입니다. 결과값을 회원가입시에 넣어주세요.")
    @GetMapping("/code/verify")
    public BaseResponse<MemberCodeVerifyResponse> verifyMemberCode(@RequestParam String code) {
        return BaseResponse.onSuccess(memberMemberAdviser.verifyAppCode(code));
    }

    @Operation(summary = "사용자 프로필 사진 변경 API", description = "사용자의 사진 변경하는 API 입니다")
    @PatchMapping(path = "/profile", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public BaseResponse<MemberIdResponse> modifyMyProfileAvatar(@CurrentMember Member member,
                                                                @RequestPart("file") MultipartFile file) {
        return BaseResponse.onSuccess(memberMemberAdviser.modifyMyProfileAvatar(member));
    }

    @Operation(summary = "사용자 프로필 조회 API", description = "사용자의 Id로 프로필을 조회하는 API 입니다")
    @GetMapping("/profile/{memberId}")
    public BaseResponse<MemberSearchResponse> getProfile(@CurrentMember Member member,
                                                         @PathVariable(name = "memberId") Long memberId) {
        return BaseResponse.onSuccess(memberMemberAdviser.getProfile(memberId));
    }
}
