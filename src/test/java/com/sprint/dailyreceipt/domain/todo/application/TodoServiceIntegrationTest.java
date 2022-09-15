package com.sprint.dailyreceipt.domain.todo.application;

import com.sprint.dailyreceipt.domain.todo.dao.TodoRepository;
import com.sprint.dailyreceipt.support.AbstractIntegrationTest;
import com.sprint.dailyreceipt.domain.todo.api.model.TodoCreateRequest;
import com.sprint.dailyreceipt.domain.todo.api.model.TodoUpdateResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("TodoService Integration Test")
public class TodoServiceIntegrationTest extends AbstractIntegrationTest {

    @Autowired
    private TodoService todoService;

    @Autowired
    private TodoRepository todoRepository;

    @Test
    @DisplayName("save() : Todo 항목이 정상일 경우, 정상적으로 Todo를 저장할 수 있다")
    void testSave_Success() throws Exception {
        //given
        TodoCreateRequest request = TodoCreateRequest.builder()
                                                     .task("ATDD 공부")
                                                     .timer("270")
                                                     .isDone(false)
                                                     .date(LocalDate.now())
                                                     .build();

        int beforeSavedSize = todoRepository.findAll().size();

        //when
        long savedId = todoService.save(request);
        int afterSavedSize = todoRepository.findAll().size();

        //then
        assertEquals(afterSavedSize, beforeSavedSize + 1);
    }

    @Test
    @DisplayName("update() : 변경할 Todo 항목이 정상일 경우, 정상적으로 Todo를 수정할 수 있다")
    void testUpdate_Success() throws Exception {
        //given
        TodoCreateRequest updateRequest = TodoCreateRequest.builder()
                                                           .task("ATDD 공부")
                                                           .timer("270")
                                                           .isDone(false)
                                                           .date(LocalDate.now())
                                                           .build();

        TodoUpdateResponse updateResponse = new TodoUpdateResponse("ATDD 공부", "270", false);

        //when
        TodoUpdateResponse updatedTodo = todoService.update(updateRequest, 1L);

        //then
        Assertions.assertAll(
                () -> assertEquals(updatedTodo.getTask(), "ATDD 공부"),
                () -> assertEquals(updatedTodo.getTimer(), "270"),
                () -> assertFalse(updateRequest.isDone())
        );
    }
}
