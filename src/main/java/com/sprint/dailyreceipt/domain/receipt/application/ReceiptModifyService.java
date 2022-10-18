package com.sprint.dailyreceipt.domain.receipt.application;

import com.sprint.dailyreceipt.domain.account.dao.AccountDao;
import com.sprint.dailyreceipt.domain.account.entity.Account;
import com.sprint.dailyreceipt.domain.account.exception.AccountMisMatchException;
import com.sprint.dailyreceipt.domain.receipt.dao.ReceiptDao;
import com.sprint.dailyreceipt.domain.receipt.entity.Receipt;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ReceiptModifyService {

    private final ReceiptDao receiptDao;

    public void modify(Account account, long receiptId) {
        Receipt savedReceipt = receiptDao.findReceiptByReceiptId(receiptId);

        if (account.isNotSame(savedReceipt.getAccount())) {
            throw new AccountMisMatchException();
        }

        savedReceipt.unpin();
    }
}
