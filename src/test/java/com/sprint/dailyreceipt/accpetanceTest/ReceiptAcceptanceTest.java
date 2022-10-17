package com.sprint.dailyreceipt.accpetanceTest;

import com.sprint.dailyreceipt.domain.receipt.api.model.ReceiptRegisterRequest;
import com.sprint.dailyreceipt.support.AbstractAcceptanceTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Receipt Acceptance Test")
public class ReceiptAcceptanceTest extends AbstractAcceptanceTest {


    @Test
    @DisplayName("POST /api/v1/receipt : Receipt 생성 요청이 정상적으로 수행될 경우, Created(201)이 반환된다")
    void testPostCreateReceiptStatusCreated() throws Exception {
        //given
        ReceiptRegisterRequest request = ReceiptRegisterRequest.builder()
                                                               .todoIds(List.of(1, 2, 3))
                                                               .pinned(true)
                                                               .famousSaying("say")
                                                               .name("name1")
                                                               .build();

        HttpEntity httpEntity = createHttpEntity(request);

        //when
        ResponseEntity<Long> response = template().postForEntity("/api/v1/receipt", httpEntity, Long.class);

        //then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    }
}
