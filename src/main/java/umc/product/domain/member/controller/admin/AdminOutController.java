package umc.product.domain.member.controller.admin;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import umc.product.domain.member.adviser.admin.AdminOutAdviser;
import umc.product.domain.member.dto.response.member.out.MemberOutIdResponse;
import umc.product.domain.member.entity.enums.OutReason;
import umc.product.global.common.base.BaseResponse;

@Tag(name = "운영진용(Web) Member OUT API", description = "운영진용(Web) Member OUT 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/web/admin/members")
public class AdminOutController {
    private final AdminOutAdviser adminOutAdviser;

    @Operation(summary = "OUT 부여 API", description = "OUT을 부여하는 API입니다. 3OUT시 자동 OUT 처리됩니다.")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "사용자 OUT 부여 성공"
            )
    })
    @Parameters({
            @Parameter(name = "memberId", description = "부여하려는 Out의 사용자 Id"),
    })
    @PostMapping("/out/{memberId}")
    public BaseResponse<MemberOutIdResponse> postMemberOut(
            @PathVariable(name = "memberId") Long memberId,
            @RequestParam @Parameter(name = "OutReason", description = "부여하려는 Out의 이유") OutReason outReason
    ) {
        return BaseResponse.onSuccess(adminOutAdviser.postMemberOut(memberId, outReason));
    }

    @Operation(summary = "OUT 내용 수정 API", description = "OUT 내용 수정하는 API입니다.")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "사용자 OUT 수정 성공"
            )
    })
    @Parameters({
            @Parameter(name = "memberId", description = "수정하려는 Out의 사용자 Id"),
            @Parameter(name = "outId", description = "수정하려는 Out의 Id"),
    })
    @PatchMapping("/out/{memberId}/{outId}")
    public BaseResponse<MemberOutIdResponse> modifyMemberOut(
            @PathVariable(name = "memberId") Long memberId,
            @PathVariable(name = "outId") Long outId,
            @RequestParam @Parameter(name = "OutReason", description = "부여하려는 Out의 이유") OutReason outReason
    ) {
        return BaseResponse.onSuccess(adminOutAdviser.modifyMemberOut(outId, memberId, outReason));
    }

    @Operation(summary = "OUT 취소(삭제) API", description = "OUT 취소(삭제)하는 API입니다.")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "사용자 OUT 삭제 성공"
            )
    })
    @Parameters({
            @Parameter(name = "memberId", description = "삭제하려는 Out의 사용자 Id"),
            @Parameter(name = "outId", description = "삭제하려는 Out의 Id"),
    })
    @DeleteMapping("/out/{memberId}/{outId}")
    public BaseResponse<MemberOutIdResponse> deleteMemberOut(
            @PathVariable(name = "memberId") Long memberId,
            @PathVariable(name = "outId") Long outId
    ) {
        return BaseResponse.onSuccess(adminOutAdviser.deleteMemberOut(memberId, outId));
    }
}
