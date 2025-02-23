package umc.product.domain.member.dto.response.member;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import umc.product.domain.member.entity.enums.Role;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class MemberRoleResponse {
    String university;
    List<MemberCodePropertiesResponse> memberCodePropertiesList;
}
