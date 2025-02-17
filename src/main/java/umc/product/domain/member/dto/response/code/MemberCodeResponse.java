package umc.product.domain.member.dto.response.code;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class MemberCodeResponse {
    private String code;
}
