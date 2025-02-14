package umc.product.domain.event.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum EventType {
    // 힉교, 지부, 중앙
    UNIVERSITY("학교"),
    DEPARTMENT("지부"),
    CENTRAL("중앙")
    ;

    private final String description;
}
