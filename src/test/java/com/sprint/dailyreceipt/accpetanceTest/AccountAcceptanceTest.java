package com.sprint.dailyreceipt.accpetanceTest;

import com.sprint.dailyreceipt.domain.account.api.model.AccountInfoResponse;
import com.sprint.dailyreceipt.domain.account.api.model.AccountUpdateRequest;
import com.sprint.dailyreceipt.support.AbstractAcceptanceTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Account Acceptance Test")
public class AccountAcceptanceTest extends AbstractAcceptanceTest {


    @Test
    @DisplayName("GET /api/v1/user : 사용자 정보 조회 요청이 정상적으로 수행될 경우, OK(200)이 반환된다")
    void testGetSearchAccountInfoStatusOK() throws Exception {
        //given
        HttpEntity httpEntity = createHttpEntity();

        //when
        ResponseEntity<AccountInfoResponse> response = template().exchange("/api/v1/user", HttpMethod.GET, httpEntity,
                                                                           AccountInfoResponse.class);

        //then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    @DisplayName("PUT /api/v1/user : 사용자 정보 수정 요청이 정상적으로 수행될 경우, OK(200)이 반환된다")
    void testPutUpdateAccountStatusOK() throws Exception {
        //given
        AccountUpdateRequest accountUpdateRequest = new AccountUpdateRequest("user2");

        HttpEntity httpEntity = createHttpEntity(accountUpdateRequest);

        //when
        ResponseEntity<AccountInfoResponse> response = template().exchange("/api/v1/user", HttpMethod.PUT, httpEntity,
                                                                           AccountInfoResponse.class);

        //then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }
}
