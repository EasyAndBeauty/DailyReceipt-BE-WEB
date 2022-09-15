package com.sprint.dailyreceipt.accpetanceTest;

import com.sprint.dailyreceipt.domain.token.entity.Token;
import com.sprint.dailyreceipt.domain.token.dao.TokenRepository;
import com.sprint.dailyreceipt.support.AbstractAcceptanceTest;
import com.sprint.dailyreceipt.domain.token.api.model.TokenResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;

public class TokenAcceptanceTest extends AbstractAcceptanceTest {

    @Autowired
    TestRestTemplate restTemplate;

    @Autowired
    TokenRepository tokenRepository;

    @Test
    @DisplayName("POST /api/v1/tokens/re-issue : Token 재발급이 정상적으로 수행될 경우, OK(200)이 반환된다")
    void testPostReIssueStatusOK() throws Exception {
        //given
        Token token = tokenRepository.findById(1L).get();

        HttpHeaders headers = new HttpHeaders();

        headers.setBearerAuth(token.getRefreshToken());

        HttpEntity<Void> request = new HttpEntity<>(headers);

        //when
        ResponseEntity<TokenResponse> response = restTemplate.postForEntity(
                "/api/v1/tokens/re-issuance", request, TokenResponse.class);

        //then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }
}
