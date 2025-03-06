package umc.product.domain.study.entity.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Check {

    YES("○"),
    NO("X"),
    UNSET("미입력"),
    PARTIAL("△")
    ;
    private final String name;
}
