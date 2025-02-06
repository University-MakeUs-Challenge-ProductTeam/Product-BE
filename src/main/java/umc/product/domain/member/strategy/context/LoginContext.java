package com.example.groutine.domain.member.strategy.context;

import com.example.groutine.domain.member.entity.LoginType;
import com.example.groutine.domain.member.dto.response.MemberLoginResponse;
import com.example.groutine.domain.member.strategy.LoginStrategy;
import com.example.groutine.domain.member.strategy.impl.AnonymousLoginStrategy;
import com.example.groutine.domain.member.strategy.impl.KakaoLoginStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class LoginContext {

    private final Map<LoginType, LoginStrategy> strategyMap;

    @Autowired
    public LoginContext(List<LoginStrategy> strategies) {
        this.strategyMap = new HashMap<>();
        strategies.forEach(strategy -> {
            if (strategy instanceof KakaoLoginStrategy) {
                strategyMap.put(LoginType.KAKAO, strategy);
            } else if (strategy instanceof AnonymousLoginStrategy) {
                strategyMap.put(LoginType.ANONYMOUS, strategy);
            }
        });
    }

    public MemberLoginResponse executeStrategy(String accessToken, LoginType loginType) {
        LoginStrategy strategy = strategyMap.get(loginType);
        if (strategy == null) {
            throw new IllegalArgumentException("Unsupported login type: " + loginType);
        }
        return strategy.login(accessToken);
    }
}
