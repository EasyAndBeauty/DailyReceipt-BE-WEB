package com.sprint.dailyreceipt.domain.receipt.dao;

import com.sprint.dailyreceipt.domain.receipt.entity.Receipt;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReceiptRepository extends JpaRepository<Receipt, Long> {
}
