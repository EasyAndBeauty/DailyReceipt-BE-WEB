package com.sprint.dailyreceipt.domain.account.application;

import com.sprint.dailyreceipt.domain.account.api.model.AccountInfoResponse;
import com.sprint.dailyreceipt.domain.account.api.model.AccountUpdateRequest;
import com.sprint.dailyreceipt.domain.account.dao.AccountDao;
import com.sprint.dailyreceipt.domain.account.entity.Account;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


class AccountUpdateServiceTest {

    AccountDao accountDao;

    AccountUpdateService accountUpdateService;

    @BeforeEach
    void init() {
        accountDao = mock(AccountDao.class);

        accountUpdateService = new AccountUpdateService(accountDao);
    }

    @Test
    @DisplayName("update() : 사용자 정보 수정 요청을 통해 사용자 정보를 수정할 수 있다.")
    void testUpdate() throws Exception {
        //given
        Account account = Account.builder()
                                 .id(1L)
                                 .nickname("account1")
                                 .build();

        AccountUpdateRequest accountUpdateRequest = new AccountUpdateRequest("account2");

        //when
        when(accountDao.findAccountByAccountId(anyLong()))
                .thenReturn(account);

        AccountInfoResponse result = accountUpdateService.update(account, accountUpdateRequest);

        //then
        assertThat(result.getNickname()).isEqualTo("account2");
    }
}