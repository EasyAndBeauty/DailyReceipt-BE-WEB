package com.sprint.dailyreceipt.domain.receipt.dao;

import com.sprint.dailyreceipt.domain.receipt.entity.Receipt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReceiptRepository extends JpaRepository<Receipt, Long> {

    @Query("select r from Receipt r join fetch r.account where r.account.id=:id")
    List<Receipt> findReceiptByAccountId(@Param("id") long accountId);
}
