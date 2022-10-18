package com.sprint.dailyreceipt.domain.receipt.application;

import com.sprint.dailyreceipt.domain.account.entity.Account;
import com.sprint.dailyreceipt.domain.receipt.dao.ReceiptDao;
import com.sprint.dailyreceipt.domain.receipt.entity.Receipt;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ReceiptProfileServiceTest {

    ReceiptDao receiptDao;

    ReceiptProfileService receiptProfileService;

    @BeforeEach
    void init() {
        receiptDao = mock(ReceiptDao.class);

        receiptProfileService = new ReceiptProfileService(receiptDao);
    }

    @Test
    @DisplayName("findReceiptList() : 유저 정보가 주어질 경우, pin 되어 있는 Receipt List를 반환할 수 있다.")
    void testFindReceiptListWithPinned() throws Exception {
        //given
        Account account = Account.builder()
                                 .nickname("user1")
                                 .id(1L)
                                 .build();

        Receipt receipt1 = Receipt.builder()
                                  .account(account)
                                  .pinned(true)
                                  .famousSaying("aa")
                                  .build();

        Receipt receipt2 = Receipt.builder()
                                  .account(account)
                                  .pinned(false)
                                  .famousSaying("bb")
                                  .build();

        List<Receipt> receiptList = List.of(receipt1, receipt2);

        //when
        when(receiptDao.findReceiptByAccountId(anyLong()))
                .thenReturn(receiptList);

        List<Receipt> result = receiptList.stream()
                                          .filter(Receipt::isPinned)
                                          .collect(Collectors.toList());

        //then
        assertAll(
                () -> assertThat(result.size()).isEqualTo(1),
                () -> assertThat(result.get(0).getFamousSaying()).isEqualTo("aa")
        );
    }
}