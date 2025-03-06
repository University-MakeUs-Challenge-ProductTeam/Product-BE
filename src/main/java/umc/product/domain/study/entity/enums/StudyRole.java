package umc.product.domain.study.entity.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum StudyRole {

    LEADER("스터디 리더"),
    CHALLENGER("스터디원")
    ;
    private final String name;
}
