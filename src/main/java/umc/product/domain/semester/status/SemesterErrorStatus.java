package umc.product.domain.semester.status;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import umc.product.global.common.exception.code.BaseCodeDto;
import umc.product.global.common.exception.code.BaseCodeInterface;

@Getter
@AllArgsConstructor
public enum SemesterErrorStatus implements BaseCodeInterface {

    EMPTY_SEMESTER(HttpStatus.NOT_FOUND, "SEMESTER404", "기수를 찾을 수 없습니다."),
    NOT_MATCH_POSITION_MEMBER(HttpStatus.BAD_REQUEST, "SEMESTER-POSITION400", "사용자에게 없는 직책 id 입니다"),

    NOT_MATCH_PART_MEMBER(HttpStatus.BAD_REQUEST, "SEMESTER-PART400", "사용자에게 없는 파트 id 입니다")
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
