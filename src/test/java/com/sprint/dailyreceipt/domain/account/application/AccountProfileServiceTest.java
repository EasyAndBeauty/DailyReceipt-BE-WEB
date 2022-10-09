package com.sprint.dailyreceipt.domain.account.application;

import com.sprint.dailyreceipt.domain.account.api.model.AccountInfoResponse;
import com.sprint.dailyreceipt.domain.account.dao.AccountDao;
import com.sprint.dailyreceipt.domain.account.entity.Account;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@DisplayName("AccountProfileService Unit Test")
class AccountProfileServiceTest {

    AccountDao accountDao;

    AccountProfileService accountProfileService;

    @BeforeEach
    void init() {
        accountDao = mock(AccountDao.class);

        accountProfileService = new AccountProfileService(accountDao);
    }

    @Test
    @DisplayName("findAccountInfo() : 사용자 정보를 조회할 수 있다")
    void testFindAccountInfo() throws Exception {
        //given
        Account account = Account.builder()
                                 .nickname("user1")
                                 .id(1L)
                                 .build();

        //when
        when(accountDao.findAccountByAccountId(anyLong()))
                .thenReturn(account);

        AccountInfoResponse accountInfo = accountProfileService.findAccountInfo(account);

        //then
        assertThat(accountInfo.getNickname()).isEqualTo(account.getNickname());
    }
}