package com.sprint.dailyreceipt.domain.token.service;

import com.sprint.dailyreceipt.domain.account.Account;
import com.sprint.dailyreceipt.domain.token.repository.TokenRepository;
import com.sprint.dailyreceipt.global.exception.jwt.NotHaveTokenException;
import com.sprint.dailyreceipt.global.jwt.JwtService;
import com.sprint.dailyreceipt.support.AbstractIntegrationTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName("TokenService Integration Test")
class TokenServiceIntegrationTest extends AbstractIntegrationTest {

    @Autowired
    JwtService jwtService;

    @Autowired
    TokenService tokenService;

    @Autowired
    TokenRepository tokenRepository;

    @Test
    @DisplayName("reIssue() : account가 토큰을 가지고 있지 않다면, NotHaveTokenException 이 발생한다")
    void testReIssue_NotHaveToken() throws Exception {
        //given
        Account account = Account.builder()
                                 .nickname("account1")
                                 .build();

        //when & Then
        assertThrows(NotHaveTokenException.class,
                     () -> tokenService.reIssue(account));
    }
}