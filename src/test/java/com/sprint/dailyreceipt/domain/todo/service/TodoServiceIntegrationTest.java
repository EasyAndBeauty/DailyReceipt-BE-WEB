package com.sprint.dailyreceipt.domain.todo.service;

import com.sprint.dailyreceipt.support.AbstractIntegrationTest;
import com.sprint.dailyreceipt.web.model.TodoCreateRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

@DisplayName("TodoService Integration Test")
public class TodoServiceIntegrationTest extends AbstractIntegrationTest {

    @Autowired
    private TodoService todoService;

    @Test
    @DisplayName("save() : Todo 항목이 정상일 경우, 정상적으로 Todo를 저장할 수 있다")
    void testSave_Success() throws Exception {
        //given
        TodoCreateRequest request = new TodoCreateRequest("TDD 공부", "250", true);

        //when
        long savedId = todoService.save(request);

        //then
        assertThat(savedId).isEqualTo(1L);
    }

    @Test
    @DisplayName("update() : 변경할 Todo 항목이 정상일 경우, 정상적으로 Todo를 수정할 수 있다")
    void testUpdate_Success() throws Exception {
        //given
        TodoCreateRequest updateRequest = new TodoCreateRequest("ATDD 공부", "270", false);

        //when
        TodoCreateRequest updatedTodo = todoService.update(updateRequest, 1L);

        //then
        Assertions.assertAll(
                () -> assertEquals(updatedTodo.getTask(), "ATDD 공부"),
                () -> assertEquals(updatedTodo.getTimer(), "270"),
                () -> assertFalse(updateRequest.isDone())
        );
    }
}
