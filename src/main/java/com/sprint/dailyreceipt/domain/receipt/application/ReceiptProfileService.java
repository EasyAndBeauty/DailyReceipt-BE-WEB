package com.sprint.dailyreceipt.domain.receipt.application;

import com.sprint.dailyreceipt.domain.account.entity.Account;
import com.sprint.dailyreceipt.domain.receipt.api.model.ReceiptInfoResponse;
import com.sprint.dailyreceipt.domain.receipt.dao.ReceiptDao;
import com.sprint.dailyreceipt.domain.receipt.entity.Receipt;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReceiptProfileService {

    private final ReceiptDao receiptDao;

    public List<ReceiptInfoResponse> findReceiptList(Account account) {
        return receiptDao.findReceiptByAccountId(account.getId())
                         .stream()
                         .filter(Receipt::isPinned)
                         .map(receipt -> ReceiptInfoResponse.of(receipt.getTodoIds(),
                                                                receipt.getFamousSaying(),
                                                                receipt.getName()))
                         .collect(Collectors.toList());
    }
}
