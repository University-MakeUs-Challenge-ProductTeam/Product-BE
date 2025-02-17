package umc.product.domain.member.mapper;

import org.springframework.stereotype.Component;
import umc.product.domain.member.dto.response.code.MemberCodeResponse;
import umc.product.domain.member.dto.response.code.MemberCodeRoleResponse;
import umc.product.domain.member.entity.enums.Role;

import java.util.List;

@Component
public class MemberCodeMapper {
    public MemberCodeResponse toMemberCode(String code){
        return MemberCodeResponse.builder()
                .code(code)
                .build();
    }

    public MemberCodeRoleResponse toMemberCodeRole(List<Role> roles) {
        return MemberCodeRoleResponse.builder()
                .roles(roles)
                .build();
    }
}

