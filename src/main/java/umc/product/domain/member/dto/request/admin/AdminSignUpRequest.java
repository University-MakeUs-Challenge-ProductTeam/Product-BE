package umc.product.domain.member.dto.request.admin;

import lombok.Getter;
import umc.product.domain.member.entity.enums.Gender;

@Getter
public class AdminSignUpRequest {
    private String name;
    private String nikeName;
    private String email;
    private String birth;
    private Gender gender;
    private String university;
    private String clientId;
    private String password;
}
