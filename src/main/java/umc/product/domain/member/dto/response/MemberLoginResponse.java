package com.example.groutine.domain.member.dto.response;

import com.example.groutine.domain.member.entity.Role;
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
