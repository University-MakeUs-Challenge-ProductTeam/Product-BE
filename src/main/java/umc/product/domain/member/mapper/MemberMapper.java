package com.example.groutine.domain.member.mapper;

import com.example.groutine.domain.member.entity.Member;
import com.example.groutine.domain.member.entity.LoginType;
import com.example.groutine.domain.member.entity.Role;
import com.example.groutine.domain.member.dto.response.MemberLoginResponse;
import com.example.groutine.global.config.security.jwt.TokenInfo;
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

