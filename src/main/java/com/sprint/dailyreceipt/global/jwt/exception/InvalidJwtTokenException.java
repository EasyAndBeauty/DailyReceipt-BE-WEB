package com.sprint.dailyreceipt.global.jwt.exception;

import com.sprint.dailyreceipt.global.exception.BusinessException;
import com.sprint.dailyreceipt.global.exception.ExceptionStatus;

public class InvalidJwtTokenException extends BusinessException {

    public InvalidJwtTokenException() {
        super(ExceptionStatus.INVALID_JWT_TOKEN_EXCEPTION);
    }
}
