//package com.sprint.dailyreceipt.domain.token.application;
//
//import com.sprint.dailyreceipt.domain.account.entity.Account;
//import com.sprint.dailyreceipt.domain.token.entity.Token;
//import com.sprint.dailyreceipt.global.jwt.application.JwtCreateService;
//import com.sprint.dailyreceipt.domain.token.api.model.TokenResponse;
//import com.sprint.dailyreceipt.global.jwt.application.JwtParseService;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.junit.jupiter.api.Assertions.assertAll;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.mock;
//import static org.mockito.Mockito.when;
//
//@DisplayName("TodoService Unit Test")
//public class TokenReIssueServiceTest {
//
//    TokenReIssueService tokenReIssueService;
//
//    JwtCreateService jwtCreateService;
//
//    JwtParseService jwtParseService;
//
//    @BeforeEach
//    void init() {
//        jwtCreateService = mock(JwtCreateService.class);
//        jwtParseService = mock(JwtParseService.class);
//
//        tokenReIssueService = new TokenReIssueService(jwtCreateService, jwtParseService);
//    }
//
//    @Test
//    @DisplayName("reIssue() : account 가 Token 을 가지고 있을 경우, 정상적으로 access, refresh 토큰을 재발급 할 수 있다")
//    void testReIssue() throws Exception {
//        //given
//        Token token = Token.builder()
//                           .refreshToken("refreshToken")
//                           .uniqueIdBySocial("unique1")
//                           .build();
//
//        Account account = Account.builder()
//                                 .token(token)
//                                 .nickname("account")
//                                 .build();
//
//        when(jwtParseService.getSubject(any()))
//                .thenReturn("unique1");
//
//        when(jwtCreateService.createAccessToken("unique1"))
//                .thenReturn("newAccessToken");
//
//        when(jwtCreateService.createRefreshToken("unique1"))
//                .thenReturn("newRefreshToken");
//
//        //when
//        TokenResponse tokenResponse = tokenReIssueService.reIssue(account);
//
//        //then
//        assertAll(
//                () -> assertThat(tokenResponse.getAccessToken()).isEqualTo("newAccessToken"),
//                () -> assertThat(tokenResponse.getRefreshToken()).isEqualTo("newRefreshToken")
//        );
//    }
//}
