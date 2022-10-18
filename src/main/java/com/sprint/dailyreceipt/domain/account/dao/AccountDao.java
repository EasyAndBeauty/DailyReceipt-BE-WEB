package com.sprint.dailyreceipt.domain.account.dao;

import com.sprint.dailyreceipt.domain.account.entity.Account;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.persistence.EntityNotFoundException;

@Component
@RequiredArgsConstructor
public class AccountDao {

    private final AccountRepository accountRepository;

    public Account findAccountByAccountId(long accountId) {
        return accountRepository.findById(accountId)
                                .orElseThrow(EntityNotFoundException::new);
    }
}
