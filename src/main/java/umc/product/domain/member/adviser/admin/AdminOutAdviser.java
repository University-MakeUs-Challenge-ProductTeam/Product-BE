package umc.product.domain.member.adviser.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import umc.product.domain.member.converter.response.MemberConverter;
import umc.product.domain.member.dto.response.member.common.MemberIdResponse;
import umc.product.domain.member.entity.Member;
import umc.product.domain.member.entity.enums.OutReason;
import umc.product.domain.member.service.admin.AdminOutService;
import umc.product.domain.member.service.member.MemberService;

@Component
@RequiredArgsConstructor
public class AdminOutAdviser {
    private final AdminOutService adminOutService;
    private final MemberService memberService;

    private final MemberConverter memberConverter;

    public MemberIdResponse postMemberOut(Long memberId, OutReason outReason) {
        Member member = memberService.findById(memberId);
        adminOutService.postMemberOut(member, outReason);
        return memberConverter.toMemberIdResponse(member.getId());
    }
    public MemberIdResponse modifyMemberOut(Long outId, OutReason outReason) {
        adminOutService.modifyMemberOut(outId, outReason);
        return memberConverter.toMemberIdResponse(outId);
    }
    public MemberIdResponse deleteMemberOut(Long memberId, Long outId) {
        Member member = memberService.findById(memberId);
        adminOutService.deleteMemberOut(member, outId);
        return memberConverter.toMemberIdResponse(outId);
    }
}
