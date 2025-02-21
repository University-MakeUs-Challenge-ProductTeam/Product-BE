package umc.product.domain.member.controller.common;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import umc.product.domain.member.adviser.common.CommonMemberAdviser;
import umc.product.domain.member.dto.response.common.MemberIdResponse;
import umc.product.domain.member.dto.response.common.MemberRoleResponse;
import umc.product.domain.member.dto.response.common.MemberSearchResponse;
import umc.product.domain.member.entity.Member;
import umc.product.global.common.base.BaseResponse;
import umc.product.global.config.security.auth.CurrentMember;

@Tag(name = "멤버 API", description = "멤버 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/common/members")
public class CommonMemberController {
    private final CommonMemberAdviser commonMemberAdviser;

    @GetMapping("/verify")
    public BaseResponse<MemberRoleResponse> verifyMemberCode(@RequestParam String code) {
        return BaseResponse.onSuccess(commonMemberAdviser.verifyMemberCode(code));
    }

    @GetMapping("/profile")
    public BaseResponse<MemberSearchResponse> getMyProfile(@CurrentMember Member member) {
        return BaseResponse.onSuccess(commonMemberAdviser.getMyProfile(member));
    }

    @PatchMapping(path = "/profile/modify", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public BaseResponse<MemberIdResponse> modifyMyProfileAvatar(@CurrentMember Member member,
                                                                @RequestPart("file") MultipartFile file) {
        return BaseResponse.onSuccess(commonMemberAdviser.modifyMyProfileAvatar(member));
    }

    @GetMapping("/profile/{memberId}")
    public BaseResponse<MemberSearchResponse> getProfile(@CurrentMember Member member,
                                                         @PathVariable(name = "memberId") Long memberId) {
        return BaseResponse.onSuccess(commonMemberAdviser.getProfile(memberId));
    }


}
