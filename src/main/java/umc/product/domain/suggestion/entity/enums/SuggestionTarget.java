package umc.product.domain.suggestion.entity.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum SuggestionTarget {
    CENTRAL("중앙"),
    BRANCH("지부"),
    UNIVERSITY("교내");

    private final String toKorean;
}
