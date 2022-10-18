package com.sprint.dailyreceipt.accpetanceTest;

import com.sprint.dailyreceipt.domain.todo.api.model.TodoCreateRequest;
import com.sprint.dailyreceipt.domain.todo.api.model.TodoInfoResponse;
import com.sprint.dailyreceipt.domain.todo.dao.TodoRepository;
import com.sprint.dailyreceipt.support.AbstractAcceptanceTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Todo Acceptance Test")
public class TodoAcceptanceTest extends AbstractAcceptanceTest {

    @Autowired
    TodoRepository todoRepository;

    @Test
    @DisplayName("POST /api/v1/todo : Todo 생성 요청이 정상적으로 수행될 경우, Created(201)이 반환된다")
    void testPostCreateTodoStatusCreated() throws Exception {
        //given
        TodoCreateRequest todoCreateRequest = TodoCreateRequest.builder()
                                                               .task("TDD 공부")
                                                               .timer("14:24")
                                                               .isDone(true)
                                                               .date(LocalDate.now())
                                                               .build();

        HttpEntity httpEntity = createHttpEntity(todoCreateRequest);

        //when
        ResponseEntity<Long> response = template().postForEntity("/api/v1/todo", httpEntity,
                                                                 Long.class);

        //then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    }

    @Test
    @DisplayName("GET /api/v1/todo : Todo 조회 요청이 정상적으로 수행될 경우, OK(200)이 반환된다")
    void testGetSearchTodoStatusOK() throws Exception {
        //given
        String targetDate = "2022-09-17";

        HttpEntity httpEntity = createHttpEntity();

        //when
        ResponseEntity<List<TodoInfoResponse>> response = template().exchange("/api/v1/todo" + "?targetDate=" + targetDate,
                                                                              HttpMethod.GET, httpEntity,
                                                                              new ParameterizedTypeReference<List<TodoInfoResponse>>() {
                                                                              });

        //then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    @DisplayName("PUT /api/v1/todo/{todo-id} : Todo 수정 요청이 정상적으로 수행될 경우, OK(200)이 반환된다")
    void testPutUpdateTodoStatusOK() throws Exception {
        //given
        TodoCreateRequest todoCreateRequest = TodoCreateRequest.builder()
                                                               .task("TDD 공부")
                                                               .timer("14:24")
                                                               .isDone(true)
                                                               .date(LocalDate.now())
                                                               .build();

        HttpEntity httpEntity = createHttpEntity(todoCreateRequest);

        //when
        ResponseEntity<TodoInfoResponse> response = template().exchange("/api/v1/todo/1", HttpMethod.PUT, httpEntity,
                                                                        TodoInfoResponse.class);

        //then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    @DisplayName("DELETE /api/v1/todo/{todo-id} : Todo 삭제 요청이 정상적으로 수행될 경우, OK(200)이 반환된다")
    void testDeleteTodoStatusOK() throws Exception {
        //given
        HttpEntity httpEntity = createHttpEntity();

        //when
        ResponseEntity<Void> response = template().exchange("/api/v1/todo/1", HttpMethod.DELETE, httpEntity,
                                                                        Void.class);

        //then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }
}
