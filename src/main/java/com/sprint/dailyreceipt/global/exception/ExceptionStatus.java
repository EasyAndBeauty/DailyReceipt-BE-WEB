package com.sprint.dailyreceipt.global.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;

@Getter
@RequiredArgsConstructor
public enum ExceptionStatus {


    // JWT
    EXPIRED_JWT_TOKEN_EXCEPTION(401, "만료된 토큰입니다.", UNAUTHORIZED),
    INVALID_JWT_TOKEN_EXCEPTION(401, "유효하지 않는 토큰입니다.", UNAUTHORIZED),
    NOT_HAVE_TOKEN_EXCEPTION(400, "토큰이 존재하지 않습니다.", BAD_REQUEST)

    ;

    private final int status;
    private final String message;
    private final HttpStatus httpStatus;
}
