package com.example.groutine.domain.member.strategy;

import com.example.groutine.domain.member.dto.response.MemberLoginResponse;

public interface LoginStrategy {
    MemberLoginResponse login(String accessToken);
}
