package umc.product.domain.member.controller.challenger;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import umc.product.domain.member.adviser.challenger.ChallengerMemberAdviser;
import umc.product.domain.member.dto.request.admin.AdminSignUpRequest;
import umc.product.domain.member.dto.response.common.MemberIdResponse;
import umc.product.domain.member.dto.response.common.MemberRoleResponse;
import umc.product.global.common.base.BaseResponse;

@Tag(name = "멤버 API", description = "멤버 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/challenger/members")
public class ChallengerMemberController {
    private final ChallengerMemberAdviser challengerMemberAdviser;

    @GetMapping("/verify")
    public BaseResponse<MemberRoleResponse> verifyMemberCode(@RequestParam String code) {
        return BaseResponse.onSuccess(challengerMemberAdviser.verifyMemberCode(code));
    }


}
