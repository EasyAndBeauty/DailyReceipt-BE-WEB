package com.sprint.dailyreceipt.domain.receipt.dao;

import com.sprint.dailyreceipt.domain.receipt.entity.Receipt;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Component
@RequiredArgsConstructor
public class ReceiptDao {

    private final ReceiptRepository receiptRepository;

    public List<Receipt> findReceiptByAccountId(long accountId) {
        return receiptRepository.findReceiptByAccountId(accountId);
    }

    public Receipt findReceiptByReceiptId(long receiptId) {
        return receiptRepository.findById(receiptId)
                                .orElseThrow(EntityNotFoundException::new);
    }
}
