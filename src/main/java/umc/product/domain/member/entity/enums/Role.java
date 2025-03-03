package umc.product.domain.member.entity.enums;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Role {
    ADMIN("관리자", 0),
    CENTRAL_ADMIN("중앙 운영진", 1),
    SCHOOL_ADMIN("학교 관리자", 2),     //web
    BRANCH_STAFF("지부 운영진", 3),      //학교 회장, 부회장
    UNIVERSITY_STAFF("교내 운영진", 4),      //교내 파트장, 운영진
    CHALLENGER("일반 챌린저", 5),
    GUEST("비회원", 6);

    private final String toKorean;
    private final int priority;
}
