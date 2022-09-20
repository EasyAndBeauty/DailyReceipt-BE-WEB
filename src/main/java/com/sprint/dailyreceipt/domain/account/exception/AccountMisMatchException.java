package com.sprint.dailyreceipt.domain.account.exception;

import com.sprint.dailyreceipt.global.exception.BusinessException;
import com.sprint.dailyreceipt.global.exception.ExceptionStatus;

public class AccountMisMatchException extends BusinessException {

    public AccountMisMatchException() {
        super(ExceptionStatus.ACCOUNT_MIS_MATCH_EXCEPTION);
    }
}
