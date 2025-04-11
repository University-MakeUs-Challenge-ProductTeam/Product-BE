package umc.product.domain.study.entity.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum StudyType {

    SCHOOL("교내"),
    BRANCH("지부"),
    ADMIN("운영진"),
    ;
    private final String name;
}
