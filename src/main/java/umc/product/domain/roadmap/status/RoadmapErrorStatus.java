package umc.product.domain.roadmap.status;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import umc.product.global.common.exception.code.BaseCodeDto;
import umc.product.global.common.exception.code.BaseCodeInterface;

@Getter
@AllArgsConstructor
public enum RoadmapErrorStatus implements BaseCodeInterface {

  ROADMAP_NOT_FOUND(HttpStatus.NOT_FOUND, "ROADMAP404", "해당 로드맵을 찾을 수 없습니다."),
  INVALID_ROADMAP_PART(HttpStatus.BAD_REQUEST, "ROADMAP_PART400", "유효하지 않은 파트 값입니다."),
  INVALID_ROADMAP_WEEK(HttpStatus.BAD_REQUEST, "ROADMAP_WEEK400", "유효하지 않은 주차 값입니다."),
  ROADMAP_SEMESTER_NOT_FOUND(HttpStatus.NOT_FOUND, "ROADMAP_SEMESTER404", "해당 로드맵 기수를 찾을 수 없습니다."),
  ROADMAP_TITLE_EMPTY(HttpStatus.BAD_REQUEST, "ROADMAP_TITLE400", "로드맵 타이틀은 비어있으면 안 됩니다."),
  ROADMAP_ALREADY_EXISTS(HttpStatus.BAD_REQUEST, "ROADMAP400", "해당 조건의 로드맵이 이미 존재합니다."),
  ROADMAP_UPDATE_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "ROADMAP500", "로드맵 수정 중 오류가 발생했습니다."),
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
