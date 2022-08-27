package com.sprint.dailyreceipt.accpetanceTest;

import com.sprint.dailyreceipt.domain.todo.repository.TodoRepository;
import com.sprint.dailyreceipt.support.AbstractAcceptanceTest;
import com.sprint.dailyreceipt.web.model.TodoCreateRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

@DisplayName("Todo API Acceptance Test")
public class TodoAcceptanceTest extends AbstractAcceptanceTest {

    @Autowired
    TestRestTemplate restTemplate;

    @Autowired
    TodoRepository todoRepository;

    @Test
    @DisplayName("POST /api/v1/todo : todo 생성이 정상적으로 수행될 경우, OK(200)이 반환된다")
    void testPostTodoStatusOK() throws Exception {
        ResponseEntity<Long> response = restTemplate.postForEntity("/api/v1/todo",
                                                                   new TodoCreateRequest("TDD 공부", "250", true),
                                                                   Long.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    @DisplayName("PUT /api/v1/todo : todo 수정이 정상적으로 수행될 경우, OK(200)이 반환된다")
    void testPutTodoStatusOK() throws Exception {
        //given
        TodoCreateRequest updateRequest = new TodoCreateRequest("ATDD 공부", "270", false);

        HttpEntity<TodoCreateRequest> updateEntity = new HttpEntity<>(updateRequest);

        //when
        ResponseEntity<TodoCreateRequest> response = restTemplate.exchange("/api/v1/todo/1", HttpMethod.PUT,
                                                                            updateEntity, TodoCreateRequest.class);

        //then
        TodoCreateRequest responseBody = response.getBody();
        Assertions.assertAll(
                () -> assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK),
                () -> assertEquals(responseBody.getTask(), "ATDD 공부"),
                () -> assertEquals(responseBody.getTimer(), "270"),
                () -> assertFalse(responseBody.isDone())
        );
    }
}
