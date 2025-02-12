package umc.product.domain.member.mapper;

import umc.product.domain.member.entity.Member;
import umc.product.domain.member.entity.LoginType;
import umc.product.domain.member.entity.Role;
import umc.product.domain.member.dto.response.MemberLoginResponse;
import umc.product.global.config.security.jwt.TokenInfo;
import org.springframework.stereotype.Component;

@Component
public class MemberMapper {
    public Member toMember(final String clientId, LoginType loginType){
        return Member.builder()
                .clientId(clientId)
                .loginType(loginType)
                .build();
    }

    public MemberLoginResponse toLoginMember(final Member member, TokenInfo tokenInfo, boolean isServiceMember, Role role) {
        return MemberLoginResponse.builder()
                .memberId(member.getId())
                .accessToken(tokenInfo.accessToken())
                .refreshToken(tokenInfo.refreshToken())
                .isServiceMember(isServiceMember)
                .role(role)
                .build();
    }
}

