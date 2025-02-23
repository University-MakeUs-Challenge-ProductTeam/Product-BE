package umc.product.domain.member.converter.response;

import org.springframework.stereotype.Component;
import umc.product.domain.member.dto.response.member.MemberCodePropertiesResponse;
import umc.product.domain.member.dto.response.member.MemberCodeResponse;
import umc.product.domain.member.dto.response.member.MemberRoleResponse;
import umc.product.domain.member.entity.MemberCode;
import umc.product.domain.member.entity.enums.Role;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class MemberCodeConverter {
    public MemberCodeResponse toMemberCodeResponse(String code){
        return MemberCodeResponse.builder()
                .code(code)
                .build();
    }

    public MemberRoleResponse toMemberRoleResponse(MemberCode memberCode) {
        return MemberRoleResponse.builder()
                .university(memberCode.getProperties().get("university").toString())
                .memberCodePropertiesList(
                        ((List<Map<String, Object>>) memberCode.getProperties().get("positionList")).stream()
                                .map(position -> MemberCodePropertiesResponse.builder()
                                        .role(Role.valueOf(position.get("role").toString()))
                                        .position(position.get("position").toString())
                                        .build())
                                .collect(Collectors.toList())
                )
                .build();
    }
}

