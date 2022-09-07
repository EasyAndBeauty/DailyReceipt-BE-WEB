package com.sprint.dailyreceipt.global.exception;

import org.springframework.http.HttpStatus;

public class BusinessException extends RuntimeException{

    private final ExceptionStatus exceptionStatus;

    public BusinessException(ExceptionStatus exceptionStatus) {
        super(exceptionStatus.getMessage());
        this.exceptionStatus = exceptionStatus;
    }

    public HttpStatus getHttpStatus() {
        return exceptionStatus.getHttpStatus();
    }

    public String getStatus() {
        return String.valueOf(exceptionStatus.getStatus());
    }
}
