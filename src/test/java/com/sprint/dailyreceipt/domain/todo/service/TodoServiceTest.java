package com.sprint.dailyreceipt.domain.todo.service;

import com.sprint.dailyreceipt.domain.account.Account;
import com.sprint.dailyreceipt.domain.account.repository.AccountRepository;
import com.sprint.dailyreceipt.domain.todo.entity.Todo;
import com.sprint.dailyreceipt.domain.todo.repository.TodoRepository;
import com.sprint.dailyreceipt.web.todo.model.TodoCreateRequest;
import com.sprint.dailyreceipt.web.todo.model.TodoUpdateResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
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

    private AccountRepository accountRepository;

    @BeforeEach
    public void init() {
        todoRepository = mock(TodoRepository.class);
        accountRepository = mock(AccountRepository.class);

        todoService = new TodoService(todoRepository, accountRepository);
    }


    @Test
    @DisplayName("save() : Todo 항목이 정상일 경우, 정상적으로 Todo를 저장할 수 있다")
    void testSave() throws Exception {
        //given
        TodoCreateRequest request = TodoCreateRequest.builder()
                                                           .task("TDD 공부")
                                                           .timer("250")
                                                           .isDone(true)
                                                           .date(LocalDate.now())
                                                           .build();

        when(todoRepository.save(any())).thenReturn(Todo.builder()
                                                        .id(1L)
                                                        .account(new Account(1L))
                                                        .task("TDD 공부")
                                                        .timer("250")
                                                        .isDone(true)
                                                        .createdAt(ZonedDateTime.now())
                                                        .updatedAt(ZonedDateTime.now())
                                                        .build());

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

        TodoUpdateResponse updateResponse = new TodoUpdateResponse("ATDD 공부", "270", false);

        TodoCreateRequest updateRequest = TodoCreateRequest.builder()
                                                           .task("ATDD 공부")
                                                           .timer("270")
                                                           .isDone(false)
                                                           .date(LocalDate.now())
                                                           .build();

        when(todoRepository.findById(anyLong()))
                .thenReturn(Optional.of(savedTodo));

        when(todoService.update(any(), anyLong())).thenReturn(updateResponse);

        //when
        TodoUpdateResponse updatedResponse = todoService.update(updateRequest, 1L);

        //then
        assertAll(
                () -> assertThat(updatedResponse.getTask()).isEqualTo("ATDD 공부"),
                () -> assertThat(updatedResponse.getTimer()).isEqualTo("270"),
                () -> assertFalse(updatedResponse.isDone())
        );
    }
}