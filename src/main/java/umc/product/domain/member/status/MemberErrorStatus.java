package com.example.groutine.domain.member.status;

import com.example.groutine.global.common.exception.code.BaseCodeDto;
import com.example.groutine.global.common.exception.code.BaseCodeInterface;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum MemberErrorStatus implements BaseCodeInterface {

    EMPTY_MEMBER(HttpStatus.NOT_FOUND, "MEMBER404", "회원을 찾을 수 없습니다."),
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "MEMBER401", "로그인을 하지 않았습니다."),
    AUTHENTICATION_FAILED(HttpStatus.UNAUTHORIZED, "MEMBER401", "로그인 정보를 찾을 수 없습니다.")
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
