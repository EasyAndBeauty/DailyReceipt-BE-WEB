package com.sprint.dailyreceipt.domain.account.dao;

import com.sprint.dailyreceipt.domain.account.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {

}
