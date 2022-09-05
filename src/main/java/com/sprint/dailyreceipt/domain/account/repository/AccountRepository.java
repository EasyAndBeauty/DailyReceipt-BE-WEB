package com.sprint.dailyreceipt.domain.account.repository;

import com.sprint.dailyreceipt.domain.account.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {

    @Query("select a from Account a where a.token.uniqueIdBySocial = :uniqueId")
    Optional<Account> findAccountByUniqueId(@Param("uniqueId") String uniqueId);
}
