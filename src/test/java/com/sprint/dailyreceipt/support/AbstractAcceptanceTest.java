package com.sprint.dailyreceipt.support;

import com.sprint.dailyreceipt.domain.account.dao.AccountRepository;
import com.sprint.dailyreceipt.domain.account.entity.Account;
import com.sprint.dailyreceipt.domain.token.dao.TokenRepository;
import com.sprint.dailyreceipt.domain.token.entity.Token;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@Sql(scripts = "/sql/data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "/sql/clean-up.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public abstract class AbstractAcceptanceTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private TokenRepository tokenRepository;

    public TestRestTemplate template() {
        return restTemplate;
    }

    protected Account testAccount() {
        return accountRepository.findById(1L).get();
    }

    protected Token testToken() {
        return tokenRepository.findById(1L).get();
    }

    protected HttpEntity createHttpEntity(Object body) {
        HttpHeaders headers = new HttpHeaders();

        headers.setBearerAuth(testToken().getRefreshToken());
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new HttpEntity(body, headers);
    }
}
