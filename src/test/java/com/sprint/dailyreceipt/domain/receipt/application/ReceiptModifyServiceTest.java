package com.sprint.dailyreceipt.domain.receipt.application;

import com.sprint.dailyreceipt.domain.receipt.dao.ReceiptDao;
import com.sprint.dailyreceipt.domain.receipt.entity.Receipt;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

@DisplayName("ReceiptModifyService Unit Test")
class ReceiptModifyServiceTest {

    ReceiptDao receiptDao;

    ReceiptModifyService receiptModifyService;

    @BeforeEach
    void init() {
        receiptDao = mock(ReceiptDao.class);

        receiptModifyService = new ReceiptModifyService(receiptDao);
    }

    @Test
    @DisplayName("modify() : Receipt 수정 요청을 통해 Receipt 를 수정할 수 있다.")
    void testModify() throws Exception {
        //given
        Receipt receipt = Receipt.builder()
                                 .pinned(true)
                                 .famousSaying("aa")
                                 .build();

        //when
        receipt.unpin();

        //then
        assertFalse(receipt.isPinned());
    }

}