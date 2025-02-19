package umc.product.domain.member.adviser.common;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import umc.product.domain.member.dto.response.common.MemberRoleResponse;
import umc.product.domain.member.dto.response.common.MemberSearchResponse;
import umc.product.domain.member.entity.Member;
import umc.product.domain.member.mapper.MemberCodeMapper;
import umc.product.domain.member.mapper.MemberMapper;
import umc.product.domain.member.service.common.MemberService;

@Component
@RequiredArgsConstructor
public class CommonMemberAdviser {
    private final MemberService memberService;

    private final MemberMapper memberMapper;
    private final MemberCodeMapper memberCodeMapper;

    public MemberRoleResponse verifyMemberCode(String code) {
        return memberCodeMapper.toMemberRoleResponse(memberService.verifyMemberCode(code));
    }
    public MemberSearchResponse getMyProfile(Member member) {
        return memberMapper.toSearchMemberResponse(member);
    }
    public MemberSearchResponse getProfile(Long memberId) {
        Member member = memberService.findById(memberId);
        return  memberMapper.toSearchMemberResponse(member);
    }



}
