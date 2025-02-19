package umc.product.domain.member.dto.request.admin;

import lombok.Getter;
import umc.product.domain.member.entity.enums.Role;

import java.util.List;

@Getter
public class AdminCodeRequest {
    // todo: university 생기면 바꿀예정
    private String university;
    private List<Role> roleList;
}
