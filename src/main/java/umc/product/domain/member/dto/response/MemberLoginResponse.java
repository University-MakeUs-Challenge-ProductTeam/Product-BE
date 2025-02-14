package umc.product.domain.member.dto.response;

import umc.product.domain.member.entity.enums.Role;
import lombok.Builder;
import lombok.Getter;


@Getter
@Builder
public class MemberLoginResponse {
    private Long memberId;
    private String accessToken;
    private String refreshToken;
    private boolean isServiceMember;
    private Role role;
}
