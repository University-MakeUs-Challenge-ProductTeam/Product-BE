package umc.product.domain.member.dto.response.member;

import lombok.Builder;
import lombok.Getter;
import umc.product.domain.member.entity.enums.Role;

@Getter
@Builder
public class MemberPositionResponse {
    private Long positionId;
    private String semester;
    private String position;
}
