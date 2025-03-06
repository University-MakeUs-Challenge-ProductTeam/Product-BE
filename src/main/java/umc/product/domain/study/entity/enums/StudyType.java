package umc.product.domain.study.entity.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum StudyType {

    SCHOOL("교내 스터디"),
    BRANCH("지부 스터디")
    ;
    private final String name;
}
