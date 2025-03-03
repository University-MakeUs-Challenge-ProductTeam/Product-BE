package umc.product.domain.member.mapper;

import org.springframework.stereotype.Component;
import umc.product.domain.member.dto.request.admin.AdminSignUpRequest;
import umc.product.domain.member.dto.request.member.MemberSignUpRequest;
import umc.product.domain.member.entity.Member;
import umc.product.domain.member.entity.MemberOut;
import umc.product.domain.member.entity.enums.*;
import umc.product.global.dto.excel.ExcelMember;

import java.util.ArrayList;
import java.util.List;

@Component
public class MemberOutMapper {

    public MemberOut toMemberOut(Member member, OutReason outReason) {
        return MemberOut.builder()
                .outReason(outReason)
                .member(member)
                .build();
    }
}

