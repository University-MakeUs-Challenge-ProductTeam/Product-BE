package umc.product.domain.event.entity.participation;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum ParticipationStatus {
    ATTENDED("참석"),
    ABSENT("불참석"),
    CANCELED("신청 취소")
    ;

    private final String description;
}
