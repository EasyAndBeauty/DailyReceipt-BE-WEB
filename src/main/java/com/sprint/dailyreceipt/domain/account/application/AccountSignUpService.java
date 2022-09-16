package com.sprint.dailyreceipt.domain.account.application;

import com.sprint.dailyreceipt.domain.account.dao.AccountRepository;
import com.sprint.dailyreceipt.domain.account.entity.Account;
import com.sprint.dailyreceipt.domain.token.entity.Token;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class AccountSignUpService {

    private final AccountRepository accountRepository;

    public Account signUp(Token token) {
        Account account = Account.of(token);

        token.addAccount(account);

        return accountRepository.save(account);
    }
}
