package com.sprint.dailyreceipt.domain.account.application;

import com.sprint.dailyreceipt.domain.account.entity.Account;
import com.sprint.dailyreceipt.domain.account.dao.AccountRepository;
import com.sprint.dailyreceipt.domain.token.dao.TokenRepository;
import com.sprint.dailyreceipt.global.jwt.application.JwtCreateService;
import com.sprint.dailyreceipt.support.AbstractIntegrationTest;
import com.sprint.dailyreceipt.infra.kakao.model.KakaoProfileResponse;
import com.sprint.dailyreceipt.domain.token.api.model.TokenResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class AccountSignInServiceTest extends AbstractIntegrationTest {

    @Autowired
    AccountSignInService accountSignInService;

    @Autowired
    JwtCreateService jwtCreateService;

    @Autowired
    TokenRepository tokenRepository;

    @Autowired
    AccountRepository accountRepository;

    @Test
    @DisplayName("signIn() : 기존에 unique ID가 존재할 경우, 성공적으로 로그인 할 수 있다")
    void test_signIn_notFirstAccount() throws Exception {
        //given
        KakaoProfileResponse kakaoProfileResponse = new KakaoProfileResponse("unique1");
        Account savedAccount = accountRepository.findAccountByUniqueId("unique1").get();
        String savedRefreshToken = savedAccount.getToken().getRefreshToken();

        //when
        TokenResponse tokenResponse = accountSignInService.signIn(kakaoProfileResponse);
        Account account = accountRepository.findAccountByUniqueId("unique1").get();
        String refreshToken = account.getToken().getRefreshToken();

        //then
        assertFalse(tokenResponse.isFirst());
        assertThat(savedRefreshToken).isNotEqualTo(refreshToken);
    }

    @Test
    @DisplayName("signIn() : 신규 Account 일 경우, 회원가입을 할 수 있다")
    void test_signIn_firstAccount() throws Exception {
        //given
        KakaoProfileResponse kakaoProfileResponse = new KakaoProfileResponse("unique5");
        int beforeSavedSize = accountRepository.findAll().size();

        //when
        TokenResponse tokenResponse = accountSignInService.signIn(kakaoProfileResponse);
        int afterSavedSize = accountRepository.findAll().size();

        //then
        assertTrue(tokenResponse.isFirst());
        assertEquals(afterSavedSize, beforeSavedSize + 1);
    }
}