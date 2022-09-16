package com.sprint.dailyreceipt.domain.token.dao;

import com.sprint.dailyreceipt.domain.token.entity.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface TokenRepository extends JpaRepository<Token, Long> {

    @Query("select t from Token t join fetch t.account")
    Optional<Token> findByUniqueIdBySocial(String uniqueIdBySocial);
}
