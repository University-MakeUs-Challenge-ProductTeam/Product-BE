package umc.product.domain.member.controller.admin;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import umc.product.domain.member.adviser.admin.AdminOutAdviser;
import umc.product.domain.member.dto.request.admin.AdminOutRequest;
import umc.product.domain.member.dto.response.member.MemberIdResponse;
import umc.product.domain.member.entity.Member;
import umc.product.domain.member.entity.enums.OutReason;
import umc.product.global.common.base.BaseResponse;
import umc.product.global.config.security.auth.CurrentMember;

@Tag(name = "어드민 Member OUT 공통 로직 API", description = "Admin Member OUT 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/web/admin/members")
public class AdminOutController {
    private final AdminOutAdviser adminOutAdviser;

    @Operation(summary = "OUT 부여 API", description = "OUT을 부여하는 API입니다. 3OUT시 자동 OUT 처리됩니다.")
    @PostMapping("/out/{memberId}")
    public BaseResponse<MemberIdResponse> postMemberOut(@CurrentMember Member member,
                                                        @PathVariable(name = "memberId") Long memberId,
                                                        @RequestParam OutReason outReason) {
        return BaseResponse.onSuccess(adminOutAdviser.postMemberOut(memberId, outReason));
    }

    @Operation(summary = "OUT 내용 수정 API", description = "OUT 내용 수정하는 API입니다.")
    @PatchMapping("/out/{outId}")
    public BaseResponse<MemberIdResponse> modifyMemberOut(@CurrentMember Member member,
                                                          @PathVariable(name = "outId") Long outId,
                                                          @RequestParam OutReason outReason) {
        return BaseResponse.onSuccess(adminOutAdviser.modifyMemberOut(outId, outReason));
    }

    @Operation(summary = "OUT 취소(삭제) API", description = "OUT 취소(삭제)하는 API입니다.")
    @DeleteMapping("/out/{memberId}/{outId}")
    public BaseResponse<MemberIdResponse> deleteMemberOut(@CurrentMember Member member,
                                                          @PathVariable(name = "memberId") Long memberId,
                                                          @PathVariable(name = "outId") Long outId) {
        return BaseResponse.onSuccess(adminOutAdviser.deleteMemberOut(memberId, outId));
    }
}
