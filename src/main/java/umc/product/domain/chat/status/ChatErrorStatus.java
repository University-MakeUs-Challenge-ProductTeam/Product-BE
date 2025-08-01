package umc.product.domain.chat.status;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import umc.product.global.common.exception.code.BaseCodeDto;
import umc.product.global.common.exception.code.BaseCodeInterface;

@Getter
@AllArgsConstructor
public enum ChatErrorStatus implements BaseCodeInterface {
    CHAT_ROOM_NOT_FOUND(HttpStatus.NOT_FOUND, "CHAT404", "채팅방을 찾을 수 없습니다."),
    ACCESS_DENIED(HttpStatus.FORBIDDEN, "CHAT403", "채팅방에 접근할 권한이 없습니다."),
    MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, "CHAT404", "사용자를 찾을 수 없습니다."),
    INVALID_MESSAGE(HttpStatus.BAD_REQUEST, "CHAT400", "잘못된 메시지입니다.");

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