package umc.product.domain.checklist.entity.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ChecklistCategory {

    ATTENDANCE("참석"),
    KEYWORD("키워드"),
    THEORY("이론"),
    PRACTICE("실습"),
    MISSION("미션")
    ;
    private final String name;
}
