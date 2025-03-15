package umc.product.domain.member.dto.response.member.auth;

import umc.product.domain.member.entity.enums.Role;
import lombok.Builder;

@Builder
public record MemberLoginResponse (
        Long memberId,
        String nickName,
        String accessToken,
        String refreshToken,
        boolean activeStatus,
        Role role
){

}
