package umc.product.domain.member.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Role {
    ADMIN("관리자", 0),
    CENTRAL_ADMIN("중앙 운영진", 1),
    BRANCH_ADMIN("지부 운영진", 2),
    UNIVERSITY_ADMIN("교내 운영진", 3),
    CHALLENGER("일반 챌린저", 4),
    GUEST("비회원", 5);

    private final String toKorean;
    private final int priority;
}
