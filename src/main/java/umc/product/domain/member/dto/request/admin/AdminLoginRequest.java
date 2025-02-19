package umc.product.domain.member.dto.request.admin;

import lombok.Getter;

@Getter
public class AdminLoginRequest {
    private String memberId;
    private String password;
}
