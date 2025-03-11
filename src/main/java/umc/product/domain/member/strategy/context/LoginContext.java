package umc.product.domain.member.strategy.context;

import umc.product.domain.member.client.SocialMemberClient;
import umc.product.domain.member.client.spec.AppleMemberClient;
import umc.product.domain.member.client.spec.GoogleMemberClient;
import umc.product.domain.member.client.spec.KakaoMemberClient;
import umc.product.domain.member.dto.request.admin.auth.AdminLoginRequest;
import umc.product.domain.member.entity.enums.LoginType;
import umc.product.domain.member.dto.response.member.auth.MemberLoginResponse;
import umc.product.domain.member.strategy.LoginStrategy;
import umc.product.domain.member.strategy.handler.LoginHandler;
import umc.product.domain.member.strategy.impl.InternalLoginStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import umc.product.domain.member.strategy.impl.SocialLoginStrategy;
import umc.product.global.common.exception.RestApiException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static umc.product.domain.member.status.MemberErrorStatus.UNSUPPORTED_LOGIN_TYPE;

@Component
@RequiredArgsConstructor
public class LoginContext {

    private final Map<LoginType, LoginHandler> clientMap;
    private final Map<LoginType, LoginStrategy> strategyMap;
    private final AppleMemberClient appleMemberClient;
    private final GoogleMemberClient googleMemberClient;
    private final KakaoMemberClient kakaoMemberClient;

    /**
     *
     * @param socialMemberClientList bean에 등록된 APPLE, GOOGLE, KAKAO 구현체 가져옴
     * @param strategyList bean에 등록된 Internal, Social 구현체 가져옴
     */
    @Autowired
    public LoginContext(
            List<SocialMemberClient> socialMemberClientList,
            List<LoginStrategy> strategyList,
            AppleMemberClient appleMemberClient,
            GoogleMemberClient googleMemberClient,
            KakaoMemberClient kakaoMemberClient
    ) {
        this.appleMemberClient = appleMemberClient;
        this.googleMemberClient = googleMemberClient;
        this.kakaoMemberClient = kakaoMemberClient;
        this.strategyMap = new HashMap<>();
        this.clientMap = new HashMap<>();

        //Social에 맞는 로직을 Map에 추가
        strategyList.forEach(strategy -> {
            if(strategy instanceof SocialLoginStrategy) {
                strategyMap.put(LoginType.KAKAO, strategy);
                strategyMap.put(LoginType.APPLE, strategy);
                strategyMap.put(LoginType.GOOGLE, strategy);
            } else if (strategy instanceof InternalLoginStrategy){        //internal
                strategyMap.put(LoginType.INTERNAL, strategy);
            }
        });

        //Social에 맞는 LoginHandler를 Map에 추가
        socialMemberClientList.forEach(client -> {
            if (client instanceof AppleMemberClient) {
                clientMap.put(LoginType.APPLE,
                        LoginHandler.builder()
                                .client(appleMemberClient)
                                .strategy(strategyMap.get(LoginType.APPLE))
                                .build());
            } else if (client instanceof GoogleMemberClient) {
                clientMap.put(LoginType.GOOGLE,
                        LoginHandler.builder()
                                .client(googleMemberClient)
                                .strategy(strategyMap.get(LoginType.GOOGLE))
                                .build());
            } else if (client instanceof KakaoMemberClient) {
                clientMap.put(LoginType.KAKAO,
                        LoginHandler.builder()
                                .client(kakaoMemberClient)
                                .strategy(strategyMap.get(LoginType.KAKAO))
                                .build());
            }
            clientMap.put(LoginType.INTERNAL,       //internal은 default로 지정
                    LoginHandler.builder()
                            .client(null)
                            .strategy(strategyMap.get(LoginType.INTERNAL))
                            .build());
        });
    }

    public MemberLoginResponse executeStrategy(
            String accessToken, LoginType loginType
    ) {
        LoginStrategy strategy = clientMap.get(loginType).getStrategy();
        if (strategy == null) {
            throw new RestApiException(UNSUPPORTED_LOGIN_TYPE);
        }
        return strategy.login(clientMap.get(loginType).getClient(), accessToken);
    }

    public MemberLoginResponse executeStrategy(
            AdminLoginRequest request
    ) {
        LoginType loginType = LoginType.INTERNAL;
        LoginStrategy strategy = clientMap.get(loginType).getStrategy();
        if (strategy == null) {
            throw new RestApiException(UNSUPPORTED_LOGIN_TYPE);
        }
        return strategy.login(null, request);
    }
}
