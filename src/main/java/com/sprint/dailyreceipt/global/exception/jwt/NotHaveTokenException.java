package com.sprint.dailyreceipt.global.exception.jwt;

import com.sprint.dailyreceipt.global.exception.BusinessException;
import com.sprint.dailyreceipt.global.exception.ExceptionStatus;

public class NotHaveTokenException extends BusinessException {

    public NotHaveTokenException() {
        super(ExceptionStatus.NOT_HAVE_TOKEN_EXCEPTION);
    }
}
