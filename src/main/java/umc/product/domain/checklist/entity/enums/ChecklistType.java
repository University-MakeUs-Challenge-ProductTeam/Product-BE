package umc.product.domain.checklist.entity.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ChecklistType {

    SELECT("select"),
    MULTIPLE("Multiple")
    ;
    private final String name;
}
