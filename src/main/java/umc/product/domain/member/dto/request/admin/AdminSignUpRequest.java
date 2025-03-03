package umc.product.domain.member.dto.request.admin;

import lombok.Getter;

@Getter
public class AdminSignUpRequest {
    private String email;
    private String university;
    private String clientId;
    private String password;
}
