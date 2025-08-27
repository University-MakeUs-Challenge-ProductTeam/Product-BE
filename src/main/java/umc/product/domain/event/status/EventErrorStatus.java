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

    //EVENT_MEMBER
    EVENT_MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, "EVENT_MEMBER401", "행사멤버를 찾을 수 없습니다."),

    //EVENT_REVIEW
    EVENT_REVIEW_NOT_FOUND(HttpStatus.NOT_FOUND, "EVENT_REVIEW401", "행사 댓글을 찾을 수 없습니다."),

    //EVENT_PARTICIPATION
    EVENT_PARTICIPATION_NOT_FOUND(HttpStatus.NOT_FOUND, "EVENT_PARTICIPATION401", "행사 참여 정보를 찾을 수 없습니다.");

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
