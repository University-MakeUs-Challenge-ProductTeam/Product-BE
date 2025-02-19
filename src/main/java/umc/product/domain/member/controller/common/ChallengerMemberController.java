package umc.product.domain.member.controller.common;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import umc.product.domain.member.adviser.challenger.ChallengerMemberAdviser;
import umc.product.domain.member.dto.response.common.MemberRoleResponse;
import umc.product.global.common.base.BaseResponse;

@Tag(name = "멤버 API", description = "멤버 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/common/members")
public class ChallengerMemberController {
    private final ChallengerMemberAdviser challengerMemberAdviser;

    @GetMapping("/verify")
    public BaseResponse<MemberRoleResponse> verifyMemberCode(@RequestParam String code) {
        return BaseResponse.onSuccess(challengerMemberAdviser.verifyMemberCode(code));
    }


}
