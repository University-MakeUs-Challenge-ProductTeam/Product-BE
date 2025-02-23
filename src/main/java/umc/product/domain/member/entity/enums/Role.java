package umc.product.domain.member.entity.enums;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Role {
    ADMIN("관리자", 0),        //중앙 총괄, 부총괄
    CENTRAL_ADMIN("중앙 운영진", 1),     //국장, 파트장
    BRANCH_ADMIN("지부 운영진", 2),      //학교 회장, 부회장
    UNIVERSITY_ADMIN("교내 운영진", 3),      //교내 파트장, 운영진
    CHALLENGER("일반 챌린저", 4),
    GUEST("비회원", 5);

    private final String toKorean;
    private final int priority;
}
