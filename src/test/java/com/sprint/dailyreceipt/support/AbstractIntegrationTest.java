package com.sprint.dailyreceipt.support;

import com.sprint.dailyreceipt.domain.account.dao.AccountRepository;
import com.sprint.dailyreceipt.domain.account.entity.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
@Sql(scripts = "/sql/data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "/sql/clean-up.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public abstract class AbstractIntegrationTest {

    @Autowired
    private AccountRepository accountRepository;

    protected Account defaultAccount() {
        return testAccount(1L);
    }

    protected Account testAccount(long accountId) {
        return accountRepository.findById(accountId).get();
    }

}
