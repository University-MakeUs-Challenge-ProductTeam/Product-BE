package umc.product.domain.member.dto.request.code;

import lombok.Getter;
import umc.product.domain.member.entity.enums.Role;

import java.util.List;

@Getter
public class MemberAdminCodeRequest {
    // todo: university 생기면 바꿀예정
    private String university;
    private List<Role> roles;
}
