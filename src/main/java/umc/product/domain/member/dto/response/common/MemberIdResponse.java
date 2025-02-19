package umc.product.domain.member.dto.response.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class MemberIdResponse {
    private Long memberId;
}
