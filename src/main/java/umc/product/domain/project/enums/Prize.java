package umc.product.domain.project.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Prize {

    FIRST("대상"),
    SECOND("최우수상"),
    THIRD("우수상")
    ;
    private final String name;
}