package umc.product.domain.member.client.spec;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import umc.product.domain.member.client.SocialMemberClient;
import umc.product.domain.member.client.common.SocialClient;
import umc.product.domain.member.dto.client.SocialLoginResponse;
import umc.product.domain.member.entity.enums.LoginType;
import umc.product.global.common.exception.RestApiException;

import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.RSAPublicKeySpec;
import java.util.Base64;
import java.util.List;
import java.util.Map;

import static umc.product.domain.member.status.AuthErrorStatus.FAILED_GET_APPLE_KEY;

@Component
@RequiredArgsConstructor
public class AppleMemberClient implements SocialMemberClient {
    private final SocialClient socialClient;
    private static final String APPLE_KEYS_URL = "https://appleid.apple.com/auth/keys";
    private static final String APPLE_ISSUER_URL = "https://appleid.apple.com";

    @Override
    public SocialLoginResponse getSocialLoginResponse(
            String accessToken
    ) throws Exception {
        DecodedJWT decodedJWT = JWT.decode(accessToken);

        List<Map<String, Object>> keys = socialClient.getPublicKeys(APPLE_KEYS_URL);
        for (Map<String, Object> key : keys) {
            String kid = decodedJWT.getKeyId();
            if (kid.equals(key.get("kid"))) {
                String n = (String) key.get("n");
                String e = (String) key.get("e");

                // X.509 공개 키 생성
                RSAPublicKey rsaPublicKey = getRSAPublicKey(n, e);

                // 서명 검증
                Algorithm algorithm = Algorithm.RSA256(rsaPublicKey, null);
                JWTVerifier verifier = JWT.require(algorithm)
                        .withIssuer(APPLE_ISSUER_URL)
                        .build();

                // 서명 검증 및 사용자 정보 추출
                verifier.verify(accessToken);

                String id = decodedJWT.getClaim("sub").asString();
                System.out.println(id);
                return SocialLoginResponse.builder()
                        .id(id)
                        .build();
            }
        }
        throw new RestApiException(FAILED_GET_APPLE_KEY);
    }

    @Override
    public LoginType getLoginType(
    ) {
        return LoginType.APPLE;
    }

    // X.509 공개 키 생성
    private RSAPublicKey getRSAPublicKey(
            String n,
            String e
    ) throws Exception {
        // 'n'과 'e' 값을 디코딩하고, 공개 키를 생성
        byte[] modulus = Base64.getUrlDecoder().decode(n);
        byte[] exponent = Base64.getUrlDecoder().decode(e);

        // 공개 키 객체 생성
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        RSAPublicKey publicKey = (RSAPublicKey) keyFactory.generatePublic(new RSAPublicKeySpec(new BigInteger(1, modulus), new BigInteger(1, exponent)));
        return publicKey;
    }
}