package umc.product.domain.member.service.admin;

import umc.product.domain.member.entity.Member;
import umc.product.domain.member.entity.MemberOut;
import umc.product.domain.member.entity.enums.OutReason;

public interface AdminOutService {

    MemberOut postMemberOut(Member member, OutReason outReason);
    void modifyMemberOut(Long outId, OutReason outReason);
    void deleteMemberOut(Member member,Long outId);
}
