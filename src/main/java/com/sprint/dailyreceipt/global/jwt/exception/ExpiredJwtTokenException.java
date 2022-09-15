package com.sprint.dailyreceipt.global.jwt.exception;

import com.sprint.dailyreceipt.global.exception.BusinessException;
import com.sprint.dailyreceipt.global.exception.ExceptionStatus;

public class ExpiredJwtTokenException extends BusinessException {

    public ExpiredJwtTokenException() {
        super(ExceptionStatus.EXPIRED_JWT_TOKEN_EXCEPTION);
    }
}
