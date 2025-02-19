package umc.product.domain.member.dto.request.common;

import lombok.Getter;
import umc.product.domain.member.entity.enums.Gender;
import umc.product.domain.member.entity.enums.LoginType;
import umc.product.domain.member.entity.enums.Role;
import umc.product.global.common.enums.Status;

@Getter
public class CommonSignUpRequest {
    private Role role;      //교내 or 챌린저
    private String name;
    private String nikeName;
    private String email;
    private LoginType loginType;
    private String birth;
    private Gender gender;
    private String clientId;
    private String password;
    private String code;
}
