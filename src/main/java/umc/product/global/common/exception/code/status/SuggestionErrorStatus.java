package umc.product.global.common.exception.code.status;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import umc.product.global.common.exception.code.BaseCodeDto;
import umc.product.global.common.exception.code.BaseCodeInterface;

@Getter
@AllArgsConstructor
public enum SuggestionErrorStatus implements BaseCodeInterface {

    SUGGESTION_NOT_EXIST(HttpStatus.BAD_REQUEST, "SUGG001", "건의함이 없습니다."),
    SUGGESTION_NOT_AUTH(HttpStatus.BAD_REQUEST, "SUGG002", "글 작성자 본인만 권한이 있습니다"),

    SUGGESTION_COMMENT_NOT_EXIST(HttpStatus.BAD_REQUEST, "COMM001", "댓글이 없습니다"),
    ;

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
