package umc.product.domain.member.client.spec;

import lombok.RequiredArgsConstructor;
import umc.product.domain.member.client.SocialMemberClient;
import umc.product.domain.member.client.common.SocialClient;
import umc.product.domain.member.dto.client.SocialLoginResponse;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class KakaoMemberClient implements SocialMemberClient {
    private final SocialClient socialClient;
    private static final String KAKAO_URL = "https://kapi.kakao.com/v2/user/me";

    @Override
    public SocialLoginResponse getSocialLoginResponse(String accessToken) {
        String idPath = "$.id";

        return socialClient.getSocialLoginResponse(accessToken, KAKAO_URL, idPath);
    }
}