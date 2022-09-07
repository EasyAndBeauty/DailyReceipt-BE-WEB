package com.sprint.dailyreceipt.global.exception;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ExceptionResponseInfo {

    private final String status;
    private final String message;

    public static ExceptionResponseInfo from(BusinessException businessException) {
        return new ExceptionResponseInfo(businessException.getStatus(), businessException.getMessage());
    }
}
