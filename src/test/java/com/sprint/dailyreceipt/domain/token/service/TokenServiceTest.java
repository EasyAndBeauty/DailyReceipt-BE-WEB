package com.sprint.dailyreceipt.domain.token.service;

import com.sprint.dailyreceipt.domain.account.Account;
import com.sprint.dailyreceipt.domain.token.entity.Token;
import com.sprint.dailyreceipt.global.jwt.JwtService;
import com.sprint.dailyreceipt.web.token.model.TokenResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@DisplayName("TodoService Unit Test")
public class TokenServiceTest {

    TokenService tokenService;

    JwtService jwtService;

    @BeforeEach
    void init() {
        jwtService = mock(JwtService.class);

        tokenService = new TokenService(jwtService);
    }

    @Test
    @DisplayName("reIssue() : account 가 Token 을 가지고 있을 경우, 정상적으로 access, refresh 토큰을 재발급 할 수 있다")
    void testReIssue() throws Exception {
        //given
        Token token = Token.builder()
                           .refreshToken("refreshToken")
                           .uniqueIdBySocial("unique1")
                           .build();

        Account account = Account.builder()
                                 .token(token)
                                 .nickname("account")
                                 .build();

        when(jwtService.getSubject(any()))
                .thenReturn("unique1");

        when(jwtService.createAccessToken("unique1"))
                .thenReturn("newAccessToken");

        when(jwtService.createRefreshToken("unique1"))
                .thenReturn("newRefreshToken");

        //when
        TokenResponse tokenResponse = tokenService.reIssue(account);

        //then
        assertAll(
                () -> assertThat(tokenResponse.getAccessToken()).isEqualTo("newAccessToken"),
                () -> assertThat(tokenResponse.getRefreshToken()).isEqualTo("newRefreshToken")
        );
    }
}
