package umc.product.domain.checklist.status;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import umc.product.global.common.exception.code.BaseCodeDto;
import umc.product.global.common.exception.code.BaseCodeInterface;

@Getter
@RequiredArgsConstructor
public enum ChecklistErrorStatus implements BaseCodeInterface {

  CHECKLIST_NOT_FOUND(HttpStatus.NOT_FOUND, "CHECKLIST404","해당 체크리스트를 찾을 수 없습니다."),
  INVALID_CHECKLIST_TYPE(HttpStatus.BAD_REQUEST, "CHECKLIST_TYPE400","잘못된 체크리스트 타입입니다.");

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
