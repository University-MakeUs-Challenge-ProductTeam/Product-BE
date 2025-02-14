package umc.product.domain.invitecode.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum InviteCodeType {

    // todo : Type 추가하기, 물어보기
    PRODUCT(""),
    AMDIM("")
    ;

    private final String description;
}
