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
    STUDY_ROADMAP_NOT_FOUND(HttpStatus.NOT_FOUND, "STUDY_ROADMAP404", "해당 스터디 로드맵을 찾을 수 없습니다."),
    STUDY_CHECKLIST_NOT_FOUND(HttpStatus.NOT_FOUND, "STUDY_CHECKLIST404", "해당 스터디 체크리스트를 찾을 수 없습니다."),
    INVALID_STUDY_TYPE_SCHOOL(HttpStatus.BAD_REQUEST, "STUDY_TYPE_SCHOOL400", "교내 스터디 조건에 맞지 않습니다."),
    INVALID_STUDY_TYPE_BRANCH(HttpStatus.BAD_REQUEST, "STUDY_TYPE_BRANCH400", "지부 스터디 조건에 맞지 않습니다."),
    UNIVERSITY_LIST_EMPTY(HttpStatus.BAD_REQUEST, "UNIVERSITY_EMPTY400", "학교 목록이 비어 있습니다."),
    UNIVERSITY_DIFFERENT_BRANCH(HttpStatus.BAD_REQUEST, "UNIVERSITY_BRANCH400", "모든 회원의 학교는 동일 지부에 속해야 합니다."),
    UNSUPPORTED_STUDY_TYPE(HttpStatus.BAD_REQUEST, "STUDY_TYPE400", "지원되지 않는 스터디 유형입니다."),
    STUDY_INFO_GET_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "STUDY_INFO500", "스터디 참여 정보 조회에 실패했습니다."),
    STUDY_MEMBER_SEMESTER_NOT_FOUND(HttpStatus.NOT_FOUND, "STUDY_MEMBER_SEMESTER404", "현재 학기에 스터디에 참여하는 사용자를 찾을 수 없습니다."),
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
