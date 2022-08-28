package com.sprint.dailyreceipt.domain.todo.service;

import com.sprint.dailyreceipt.domain.account.Account;
import com.sprint.dailyreceipt.domain.todo.entity.Todo;
import com.sprint.dailyreceipt.domain.todo.repository.TodoRepository;
import com.sprint.dailyreceipt.web.todo.model.TodoCreateRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.ZonedDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@DisplayName("TodoService Unit Test")
class TodoServiceTest {

    private TodoRepository todoRepository;

    private TodoService todoService;

    @BeforeEach
    public void init() {
        todoRepository = mock(TodoRepository.class);

        todoService = new TodoService(todoRepository);
    }


    @Test
    @DisplayName("save() : Todo 항목이 정상일 경우, 정상적으로 Todo를 저장할 수 있다")
    void testSave() throws Exception {
        //given
        TodoCreateRequest request = new TodoCreateRequest("TDD 공부", "250", true);
        when(todoRepository.save(any()))
                .thenReturn(
                        new Todo(1L, new Account(1L), "TDD 공부", "250", true, ZonedDateTime.now(), ZonedDateTime.now()));

        //when
        todoService.save(request);

        //then
        verify(todoRepository, times(1)).save(any());
    }

    //TODO : 왜 NPE인 것인가,,
    @Test
    @DisplayName("update() : 변경할 Todo 항목이 정상일 경우, 정상적으로 Todo를 수정할 수 있다")
    @Disabled("when stubbing에서 왜 NPE가 뜨는거지?")
    void testUpdate() throws Exception {
        //given
        Todo savedTodo = todoRepository.save(Todo.builder()
                                                 .id(1L)
                                                 .task("TDD 공부")
                                                 .timer("250")
                                                 .isDone(true)
                                                 .build());

        TodoCreateRequest updateRequest = new TodoCreateRequest("ATDD 공부", "270", false);

        when(todoRepository.findById(anyLong()))
                .thenReturn(Optional.of(savedTodo));

        when(todoService.update(any(), anyLong())).thenReturn(updateRequest);

        //when
        TodoCreateRequest updatedResponse = todoService.update(updateRequest, 1L);

        //then
        assertAll(
                () -> assertThat(updatedResponse.getTask()).isEqualTo("ATDD 공부"),
                () -> assertThat(updatedResponse.getTimer()).isEqualTo("270"),
                () -> assertFalse(updatedResponse.isDone())
        );
    }
}