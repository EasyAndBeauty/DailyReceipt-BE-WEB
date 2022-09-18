package com.sprint.dailyreceipt.domain.token.dao;

import com.sprint.dailyreceipt.domain.token.entity.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface TokenRepository extends JpaRepository<Token, Long> {

    @Query("select t from Token t join fetch t.account where t.uniqueIdBySocial = :id")
    Optional<Token> findByUniqueIdBySocial(@Param("id") String uniqueIdBySocial);
}
