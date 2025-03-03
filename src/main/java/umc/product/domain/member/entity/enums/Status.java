package umc.product.domain.member.entity.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Status {
    ACTIVE("활성화"),
    WAITING_FOR_UPDATE("업데이트 대기"),
    OLD("이전 기수"),
    OUT("방출"),
    DELETED("삭제");

    private final String toKorean;
}
