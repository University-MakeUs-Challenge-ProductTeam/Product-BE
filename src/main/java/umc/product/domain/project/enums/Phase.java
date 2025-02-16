package umc.product.domain.project.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Phase {

    FIRST("1차"),
    SECOND("2차"),
    THIRD("3차")
    ;
    private final String name;
}
