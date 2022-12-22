package com.sprint.dailyreceipt.domain.receipt.application;

import com.sprint.dailyreceipt.domain.account.entity.Account;
import com.sprint.dailyreceipt.domain.receipt.api.model.ReceiptRegisterRequest;
import com.sprint.dailyreceipt.domain.receipt.dao.ReceiptRepository;
import com.sprint.dailyreceipt.domain.receipt.entity.Receipt;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.ZonedDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@DisplayName("ReceiptRegisterService Unit Test")
class ReceiptRegisterServiceTest {

    ReceiptRepository receiptRepository;

    ReceiptRegisterService receiptRegisterService;

    @BeforeEach
    void init() {
        receiptRepository = mock(ReceiptRepository.class);

        receiptRegisterService = new ReceiptRegisterService(receiptRepository);
    }

    @Test
    @DisplayName("register() : Receipt 생성 요청 목록을 통해 Receipt를 생성할 수 있다")
    void testRegister() throws Exception {
        //given
        Account account = Account.builder()
                                 .nickname("user1")
                                 .build();

        ReceiptRegisterRequest request = ReceiptRegisterRequest.builder()
                                                               .todoIds(List.of(1L, 2L, 3L))
                                                               .pinned(true)
                                                               .famousSaying("say")
                                                               .name("name1")
                                                               .build();

        Receipt receipt = Receipt.builder()
                                 .id(1L)
                                 .account(account)
                                 .todoIds(request.getTodoIds())
                                 .pinned(request.isPinned())
                                 .famousSaying(request.getFamousSaying())
                                 .name(request.getName())
                                 .createdAt(ZonedDateTime.now())
                                 .updatedAt(ZonedDateTime.now())
                                 .build();

        //when
        when(receiptRepository.save(any()))
                .thenReturn(receipt);

        Long savedId = receiptRegisterService.register(account, request);

        //then
        assertThat(savedId).isEqualTo(receipt.getId());
    }
}