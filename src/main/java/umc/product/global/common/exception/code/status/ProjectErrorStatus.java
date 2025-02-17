package umc.product.global.common.exception.code.status;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import umc.product.global.common.exception.code.BaseCodeDto;
import umc.product.global.common.exception.code.BaseCodeInterface;

@Getter
@AllArgsConstructor
public enum ProjectErrorStatus implements BaseCodeInterface {

    MEMBER_PROJECT_NOT_FOUND(HttpStatus.NOT_FOUND, "MEMBER_PROJECT404", "사용자가 참여한 프로젝트를 찾을 수 없습니다."),
    PROJECT_NOT_FOUND(HttpStatus.NOT_FOUND, "PROJECT404", "프로젝트를 찾을 수 없습니다."),
    MEMBER_PROJECT_PART_NOT_FOUND(HttpStatus.NOT_FOUND, "MEMBER_PROJECT_PART404", "사용자가 참여한 프로젝트의 파트를 찾을 수 없습니다."),
    TASK_NOT_FOUND(HttpStatus.NOT_FOUND, "TASK404", "과제를 찾을 수 없습니다."),
    TASK_STATUS_NOT_FOUND(HttpStatus.NOT_FOUND, "TASK_STATUS_404", "과제 수행 여부를 찾을 수 없습니다."),
    PROJECT_DURATION_NOT_FOUND(HttpStatus.NOT_FOUND, "PROJECT_DURATION_404", "프로젝트 기간을 찾을 수 없습니다."),
    PROJECT_MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, "PROJECT_MEMBER_404", "프로젝트에 참여한 사용자를 찾을 수 없습니다."),
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
