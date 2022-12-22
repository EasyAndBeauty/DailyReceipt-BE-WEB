package com.sprint.dailyreceipt.accpetanceTest;

import com.sprint.dailyreceipt.domain.receipt.api.model.ReceiptInfoResponse;
import com.sprint.dailyreceipt.domain.receipt.api.model.ReceiptRegisterRequest;
import com.sprint.dailyreceipt.support.AbstractAcceptanceTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Receipt Acceptance Test")
public class ReceiptAcceptanceTest extends AbstractAcceptanceTest {


    @Test
    @DisplayName("POST /api/v1/receipt/pinned : Receipt 생성 요청이 정상적으로 수행될 경우, Created(201)이 반환된다")
    void testPostCreateReceiptStatusCreated() throws Exception {
        //given
        ReceiptRegisterRequest request = ReceiptRegisterRequest.builder()
                                                               .todoIds(List.of(1L, 2L))
                                                               .pinned(true)
                                                               .famousSaying("say")
                                                               .name("name1")
                                                               .build();

        HttpEntity httpEntity = createHttpEntity(request);

        //when
        ResponseEntity<Long> response = template().postForEntity("/api/v1/receipt/pinned", httpEntity, Long.class);

        //then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    }

    @Test
    @DisplayName("GET /api/v1/receipt/pinned : Receipt 조회 요청이 정상적으로 수행될 경우, OK(200)이 반환된다")
    void testGetSearchReceiptStatusOK() throws Exception {
        //given
        HttpEntity httpEntity = createHttpEntity();

        //when
        ResponseEntity<List<ReceiptInfoResponse>> response = template().exchange("/api/v1/receipt/pinned",
                                                                              HttpMethod.GET, httpEntity,
                                                                              new ParameterizedTypeReference<List<ReceiptInfoResponse>>() {
                                                                              });

        //then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    @DisplayName("PUT /api/v1/receipt : Receipt 수정 요청이 정상적으로 수행될 경우, OK(200)이 반환된다")
    void testPutModifyReceiptStatusOK() throws Exception {
        //given
        HttpEntity httpEntity = createHttpEntity();

        //when
        ResponseEntity<Void> response = template().exchange("/api/v1/receipt/1",
                                                            HttpMethod.PUT, httpEntity, Void.class);

        //then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }
}
