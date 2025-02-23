package umc.product.domain.member.dto.response.member;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class MemberCodeResponse {
    private String code;
}
