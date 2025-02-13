package umc.product.domain.project.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Prize {

    FIRST("1등"),
    SECOND("2등"),
    THIRD("3등")
    ;
    private final String prize;
}