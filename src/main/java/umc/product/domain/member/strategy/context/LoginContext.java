package umc.product.domain.member.strategy.context;

import umc.product.domain.member.dto.request.MemberLoginRequest;
import umc.product.domain.member.entity.LoginType;
import umc.product.domain.member.dto.response.MemberLoginResponse;
import umc.product.domain.member.strategy.LoginStrategy;
import umc.product.domain.member.strategy.impl.AnonymousLoginStrategy;
import umc.product.domain.member.strategy.impl.InternalLoginStrategy;
import umc.product.domain.member.strategy.impl.KakaoLoginStrategy;
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
        strategies.forEach(strategy -> { // todo: 이 부분을 switch-case로 변경
            if (strategy instanceof KakaoLoginStrategy) {
                strategyMap.put(LoginType.KAKAO, strategy);
            } else if (strategy instanceof AnonymousLoginStrategy) {
                strategyMap.put(LoginType.ANONYMOUS, strategy);
            } else if (strategy instanceof InternalLoginStrategy) {
                strategyMap.put(LoginType.INTERNAL, strategy);
            }
        });
    }

    public MemberLoginResponse executeStrategy(String accessToken, LoginType loginType) {
        LoginStrategy strategy = strategyMap.get(loginType);
        if (strategy == null) {
            // todo: RestApiException으로 예외처리 하기
            throw new IllegalArgumentException("Unsupported login type: " + loginType);
        }
        return strategy.login(accessToken);
    }

    public MemberLoginResponse executeStrategy(MemberLoginRequest request) {
        LoginType loginType = LoginType.INTERNAL;
        LoginStrategy strategy = strategyMap.get(loginType);
        if (strategy == null) {
            // todo: RestApiException으로 예외처리 하기
            throw new IllegalArgumentException("Unsupported login type: " + loginType);
        }
        return strategy.login(request);
    }
}
