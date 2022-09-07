package com.sprint.dailyreceipt.global.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ExceptionHandlerController {

    /**
     * 커스텀 예외 핸들러
     */
    @ExceptionHandler(BusinessException.class)
    private ResponseEntity<ExceptionResponseInfo> handleCustomException(BusinessException exception) {
        ExceptionResponseInfo response = ExceptionResponseInfo.from(exception);
        HttpStatus httpStatus = exception.getHttpStatus();
        errorLogging(exception);
        return new ResponseEntity<>(response, httpStatus);
    }

    private void errorLogging(Exception ex) {
        log.error("Exception = {} , message = {}", ex.getClass().getSimpleName(),
                  ex.getLocalizedMessage());
    }
}
