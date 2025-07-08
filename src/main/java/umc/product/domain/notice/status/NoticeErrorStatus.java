package umc.product.domain.notice.status;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import umc.product.global.common.exception.code.BaseCodeDto;
import umc.product.global.common.exception.code.BaseCodeInterface;

@Getter
@AllArgsConstructor
public enum NoticeErrorStatus implements BaseCodeInterface {
    NOTICE_NOT_FOUND(HttpStatus.NOT_FOUND, "NOTICE404", "공지사항을 찾을 수 없습니다."),
    CHECK_DEADLINE_EXPIRED(HttpStatus.BAD_REQUEST, "NOTICE400", "열람 체크 마감 기한이 지났습니다.");

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
