package com.sprint.dailyreceipt.domain.receipt.application;

import com.sprint.dailyreceipt.domain.account.entity.Account;
import com.sprint.dailyreceipt.domain.receipt.api.model.ReceiptRegisterRequest;
import com.sprint.dailyreceipt.domain.receipt.dao.ReceiptRepository;
import com.sprint.dailyreceipt.domain.receipt.entity.Receipt;
import com.sprint.dailyreceipt.global.annotation.Login;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;

@Service
@RequiredArgsConstructor
@Transactional
public class ReceiptRegisterService {

    private final ReceiptRepository receiptRepository;

    public Long register(@Login Account account, ReceiptRegisterRequest registerRequest) {

        Receipt receipt = Receipt.builder()
                                 .account(account)
                                 .todoIds(registerRequest.getTodoIds())
                                 .pinned(registerRequest.isPinned())
                                 .famousSaying(registerRequest.getFamousSaying())
                                 .name(registerRequest.getName())
                                 .createdAt(ZonedDateTime.now())
                                 .updatedAt(ZonedDateTime.now())
                                 .build();

        Receipt savedReceipt = receiptRepository.save(receipt);

        return savedReceipt.getId();
    }

}
