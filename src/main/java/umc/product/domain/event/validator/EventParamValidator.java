package umc.product.domain.event.validator;

import umc.product.domain.event.status.EventErrorStatus;
import umc.product.global.common.exception.RestApiException;

public class EventParamValidator {

    // 수정, 삭제 유효성 검사
    public static void validModify(Long memberId1, Long memberId2) {
        if (!memberId1.equals(memberId2))
            throw new RestApiException(EventErrorStatus.UNAUTHORIZED_MODIFY);
    }
}
