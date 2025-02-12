package umc.product.domain.member.dto.request;

import lombok.Getter;

@Getter
public class MemberLoginRequest {
    private String memberId;
    private String password;
}
