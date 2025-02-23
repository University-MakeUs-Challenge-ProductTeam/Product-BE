package umc.product.domain.member.dto.response.member;

import lombok.Builder;
import lombok.Getter;
import umc.product.domain.member.entity.enums.Role;

@Getter
@Builder
public class MemberCodePropertiesResponse {
    private Role role;
    private String position;
}
