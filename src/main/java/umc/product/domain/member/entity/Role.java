package umc.product.domain.member.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Role {
    ADMIN("관리자", 0),
    MEMBER("일반 멤버", 1),
    BUSINESS("사업자", 1),
    GUEST("비회원", 2);

    private final String toKorean;
    private final int priority;
}
