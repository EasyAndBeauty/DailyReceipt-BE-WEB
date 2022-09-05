package com.sprint.dailyreceipt.domain.token.repository;

import com.sprint.dailyreceipt.domain.token.entity.Token;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TokenRepository extends JpaRepository<Token, Long> {

    Optional<Token> findByUniqueIdBySocial(String uniqueIdBySocial);
}
