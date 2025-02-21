package umc.product.domain.member.mapper;

import org.springframework.stereotype.Component;
import umc.product.domain.member.dto.response.common.MemberCodeResponse;
import umc.product.domain.member.dto.response.common.MemberRoleResponse;
import umc.product.domain.member.entity.MemberCode;
import umc.product.domain.member.entity.enums.Role;

import java.util.List;

@Component
public class MemberCodeMapper {
    public MemberCodeResponse toMemberCodeResponse(String code){
        return MemberCodeResponse.builder()
                .code(code)
                .build();
    }

    public MemberRoleResponse toMemberRoleResponse(MemberCode memberCode) {
        return MemberRoleResponse.builder()
                .roleList(memberCode.getRoles())
                .university(memberCode.getUniversity())
                .build();
    }
}

