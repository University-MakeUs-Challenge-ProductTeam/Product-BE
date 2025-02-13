package umc.product.domain.member.dto.request;

import lombok.Getter;
import umc.product.domain.member.entity.enums.Gender;
import umc.product.domain.member.entity.enums.LoginType;
import umc.product.domain.member.entity.enums.Role;
import umc.product.global.common.enums.Status;

@Getter
public class MemberSignUpRequest {
    private Role role;
    private String name;
    private String nikeName;
    private String email;
    private LoginType loginType;
    private String avatar_url;
    private String birth;
    private Gender gender;
    private Status status;
    private String clientId;
    private String password;
}
