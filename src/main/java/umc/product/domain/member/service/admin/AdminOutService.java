package umc.product.domain.member.service.admin;

import umc.product.domain.member.entity.Member;
import umc.product.domain.member.entity.MemberOut;
import umc.product.domain.member.entity.enums.OutReason;
import umc.product.domain.study.entity.WeeklyStudyStatus;

public interface AdminOutService {

    MemberOut postMemberOut(Member member,
                            OutReason outReason);

    MemberOut postMemberOutForStudy(Member member, OutReason outReason, WeeklyStudyStatus weeklyStatus);

    void modifyMemberOut(Long outId,
                         OutReason outReason,
                         Member member);
    void deleteMemberOut(Member member,
                         Long outId);
}
