package umc.product.domain.study.status;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import umc.product.global.common.exception.code.BaseCodeDto;
import umc.product.global.common.exception.code.BaseCodeInterface;

@Getter
@AllArgsConstructor
public enum StudyErrorStatus implements BaseCodeInterface {

    STUDY_MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, "STUDY_MEMBER404", "해당 스터디에 속한 사용자를 찾을 수 없습니다."),
    STUDY_NOT_FOUND(HttpStatus.NOT_FOUND, "STUDY404", "해당 스터디를 찾을 수 없습니다."),
    STUDY_NAME_EMPTY(HttpStatus.BAD_REQUEST, "STUDY_NAME400", "스터디 이름은 비어있으면 안 됩니다."),
    STUDY_ATTENDANCE_NOT_FOUND(HttpStatus.NOT_FOUND, "STUDY_ATTENDANCE404", "해당 스터디 출석 정보를 찾을 수 없습니다."),
    INVALID_ATTENDANCE_VALUE(HttpStatus.BAD_REQUEST, "STUDY_ATTENDANCE400", "유효하지 않은 참석 여부 체크값입니다. YES, NO 중에 보내주세요."),
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
