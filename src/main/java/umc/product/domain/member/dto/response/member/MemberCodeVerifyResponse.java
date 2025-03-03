package umc.product.domain.member.dto.response.member;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class MemberCodeVerifyResponse {
    private Long memberId;
    private String name;
    private String nickName;
}
