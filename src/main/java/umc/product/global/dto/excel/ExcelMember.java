package umc.product.global.dto.excel;

import lombok.Builder;
import lombok.Getter;
import umc.product.domain.member.entity.enums.Role;
import umc.product.domain.university.entity.University;

@Builder
@Getter
public class ExcelMember {
    private String name;
    private String nickName;
    private University university;
    private String universityPosition;
    private String centralPosition;
    private Role role;
}
