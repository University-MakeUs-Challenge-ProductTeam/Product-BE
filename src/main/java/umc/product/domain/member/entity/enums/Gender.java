package umc.product.domain.member.entity.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Gender {
    FEMALE("여성"),
    MALE("남성")
    ;
    private final String toKorean;
}
