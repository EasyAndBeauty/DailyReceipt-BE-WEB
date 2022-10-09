package com.sprint.dailyreceipt.domain.account.application;

import com.sprint.dailyreceipt.domain.account.api.model.AccountInfoResponse;
import com.sprint.dailyreceipt.domain.account.api.model.AccountUpdateRequest;
import com.sprint.dailyreceipt.domain.account.dao.AccountDao;
import com.sprint.dailyreceipt.domain.account.entity.Account;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class AccountUpdateService {

    private final AccountDao accountDao;

    public AccountInfoResponse update(Account account, AccountUpdateRequest accountUpdateRequest) {
        Account savedAccount = accountDao.findAccountByAccountId(account.getId());

        Account updateAccount = Account.builder()
                                       .nickname(accountUpdateRequest.getNickname())
                                       .build();

        savedAccount.update(updateAccount);

        return new AccountInfoResponse(savedAccount);
    }
}
