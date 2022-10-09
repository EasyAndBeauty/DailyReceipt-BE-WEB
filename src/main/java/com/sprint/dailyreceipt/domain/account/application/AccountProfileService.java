package com.sprint.dailyreceipt.domain.account.application;

import com.sprint.dailyreceipt.domain.account.api.model.AccountInfoResponse;
import com.sprint.dailyreceipt.domain.account.dao.AccountDao;
import com.sprint.dailyreceipt.domain.account.entity.Account;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AccountProfileService {

    private final AccountDao accountDao;

    public AccountInfoResponse findAccountInfo(Account account) {
        Account savedAccount = accountDao.findAccountByAccountId(account.getId());

        return new AccountInfoResponse(savedAccount);
    }
}
