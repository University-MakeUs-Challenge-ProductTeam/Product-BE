package umc.product.domain.member.mapper;

import org.springframework.stereotype.Component;
import umc.product.domain.member.entity.Member;
import umc.product.domain.member.entity.MemberOut;
import umc.product.domain.member.entity.enums.*;
import umc.product.domain.study.entity.WeeklyStudyStatus;

@Component
public class MemberOutMapper {

    public MemberOut toMemberOut(Member member, OutReason outReason) {
        return MemberOut.builder()
                .outReason(outReason)
                .member(member)
                .build();
    }

    public MemberOut toMemberOut(Member member, OutReason outReason, WeeklyStudyStatus weeklyStatus) {
        return MemberOut.builder()
            .member(member)
            .outReason(outReason)
            .weeklyStudyStatus(weeklyStatus) // ★ weeklyStatus를 설정
            .build();
    }

}

