package com.sprint.dailyreceipt.domain.account.repository;

import com.sprint.dailyreceipt.domain.account.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {

    Optional<Account> findByUniqueIdBySocial(String uniqueIdBySocial);
}
