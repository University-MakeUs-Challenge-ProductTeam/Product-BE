package umc.product.domain.event.status;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import umc.product.global.common.exception.code.BaseCodeDto;
import umc.product.global.common.exception.code.BaseCodeInterface;

@Getter
@AllArgsConstructor
public enum EventErrorStatus implements BaseCodeInterface {
    UNAUTHORIZED_MODIFY(HttpStatus.BAD_REQUEST, "COMMON401", "수정, 삭제 권한이 없습니다."),

    //EVENT
    EVENT_NOT_FOUND(HttpStatus.NOT_FOUND, "EVENT401", "행사를 찾을 수 없습니다."),

    //EVENT_FORM
    EVENT_FORM_NOT_FOUND(HttpStatus.NOT_FOUND, "EVENT_FORM401", "행사 신청 폼을 찾을 수 없습니다.."),

    //PARTICIPATION_EVENT
    PARTICIPATION_EVENT_NOT_FOUND(HttpStatus.NOT_FOUND, "PARTICIPATION-EVENT401", "행사 참여를 찾을 수 없습니다.");



    private final HttpStatus httpStatus;
    private final boolean isSuccess = false;
    private final String code;
    private final String message;

    @Override
    public BaseCodeDto getCode() {
        return BaseCodeDto.builder()
                .httpStatus(httpStatus)
                .isSuccess(isSuccess)
                .code(code)
                .message(message)
                .build();
    }
}
