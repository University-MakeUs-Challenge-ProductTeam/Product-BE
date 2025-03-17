package umc.product.domain.member.client.common;

import com.jayway.jsonpath.JsonPath;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import umc.product.domain.member.dto.client.SocialLoginResponse;
import umc.product.global.common.exception.RestApiException;

import java.util.List;
import java.util.Map;

import static umc.product.domain.member.status.AuthErrorStatus.FAILED_SOCIAL_LOGIN;

/**
 * Social Login마다 공통적으로 수행하는 accessToken 검증 로직
 */
@Component
public class SocialClient {
    private final WebClient webClient;
    public SocialClient(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.build();
    }

    public SocialLoginResponse getSocialLoginResponse(
            final String accessToken,
            String url,
            String idPath
    ) {
        try {
            String response = webClient.get()
                    .uri(url)
                    .header("Authorization", "Bearer " + accessToken)
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();

            Object objectId = JsonPath.read(response, idPath);
            String id;
            if(objectId instanceof Long) id = String.valueOf(objectId);
            else id = String.valueOf(objectId);
            return SocialLoginResponse.builder()
                    .id(id)
                    .build();

        } catch (Exception e) {
            throw new RestApiException(FAILED_SOCIAL_LOGIN);
        }
    }

    /**
     * 애플 전용
     */
    public List<Map<String, Object>> getPublicKeys(
            String url
    ) {
        try {
            String response = webClient.get()
                    .uri(url)
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();

            return JsonPath.read(response, "$.keys");
        } catch (Exception e) {
            throw new RestApiException(FAILED_SOCIAL_LOGIN);
        }
    }
}
