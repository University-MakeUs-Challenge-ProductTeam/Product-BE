package umc.product.domain.member.adviser.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import umc.product.domain.member.converter.response.MemberConverter;
import umc.product.domain.member.converter.response.MemberOutConverter;
import umc.product.domain.member.dto.response.member.common.MemberIdResponse;
import umc.product.domain.member.dto.response.member.out.MemberOutIdResponse;
import umc.product.domain.member.entity.Member;
import umc.product.domain.member.entity.MemberOut;
import umc.product.domain.member.entity.enums.OutReason;
import umc.product.domain.member.service.admin.AdminOutService;
import umc.product.domain.member.service.member.MemberService;

@Component
@RequiredArgsConstructor
public class AdminOutAdviser {
    private final AdminOutService adminOutService;
    private final MemberService memberService;

    private final MemberOutConverter memberOutConverter;

    public MemberOutIdResponse postMemberOut(Long memberId, OutReason outReason) {
        Member member = memberService.findById(memberId);
        MemberOut memberOut = adminOutService.postMemberOut(member, outReason);
        return memberOutConverter.toMemberOutIdResponse(memberOut.getId(), member);
    }
    public MemberOutIdResponse modifyMemberOut(Long outId, Long memberId, OutReason outReason) {
        Member member = memberService.findById(memberId);
        adminOutService.modifyMemberOut(outId, outReason);
        return memberOutConverter.toMemberOutIdResponse(outId, member);
    }
    public MemberOutIdResponse deleteMemberOut(Long memberId, Long outId) {
        Member member = memberService.findById(memberId);
        adminOutService.deleteMemberOut(member, outId);
        return memberOutConverter.toMemberOutIdResponse(outId, member);
    }
}
