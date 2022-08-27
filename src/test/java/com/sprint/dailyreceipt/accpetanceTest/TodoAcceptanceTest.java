package com.sprint.dailyreceipt.accpetanceTest;

import com.sprint.dailyreceipt.support.AbstractAcceptanceTest;
import com.sprint.dailyreceipt.web.model.TodoCreateRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Todo API Acceptance Test")
public class TodoAcceptanceTest extends AbstractAcceptanceTest {

    @Autowired
    TestRestTemplate restTemplate;

    @Test
    @DisplayName("POST /api/v1/todo : 모든 항목이 정상이면, todo 생성이 정상적으로 수행된다")
    void testPostTodo() throws Exception {
        ResponseEntity<Long> response = restTemplate.postForEntity("/api/v1/todo",
                                                                   new TodoCreateRequest("TDD 공부", "250", true),
                                                                   Long.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(1L);
    }
}
