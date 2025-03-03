package umc.product.domain.member.dto.response.member;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MemberGenerateTokenResponse {
    private String accessToken;
    private String refreshToken;
}
