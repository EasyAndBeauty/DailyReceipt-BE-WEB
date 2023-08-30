//package com.sprint.dailyreceipt.domain.token.application;
//
//import com.sprint.dailyreceipt.domain.account.entity.Account;
//import com.sprint.dailyreceipt.domain.token.dao.TokenRepository;
//import com.sprint.dailyreceipt.global.jwt.exception.NotHaveTokenException;
//import com.sprint.dailyreceipt.global.jwt.application.JwtCreateService;
//import com.sprint.dailyreceipt.support.AbstractIntegrationTest;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//
//import static org.junit.jupiter.api.Assertions.assertThrows;
//
//@DisplayName("TokenService Integration Test")
//class TokenReIssueServiceIntegrationTest extends AbstractIntegrationTest {
//
//    @Autowired
//    JwtCreateService jwtCreateService;
//
//    @Autowired
//    TokenReIssueService tokenReIssueService;
//
//    @Autowired
//    TokenRepository tokenRepository;
//
//    @Test
//    @DisplayName("reIssue() : account가 토큰을 가지고 있지 않다면, NotHaveTokenException 이 발생한다")
//    void testReIssue_NotHaveToken() throws Exception {
//        //given
//        Account account = Account.builder()
//                                 .nickname("account1")
//                                 .build();
//
//        //when & Then
//        assertThrows(NotHaveTokenException.class,
//                     () -> tokenReIssueService.reIssue(account));
//    }
//}
