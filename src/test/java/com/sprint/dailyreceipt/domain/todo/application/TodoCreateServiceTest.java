package com.sprint.dailyreceipt.domain.todo.application;

import com.sprint.dailyreceipt.domain.account.entity.Account;
import com.sprint.dailyreceipt.domain.todo.api.model.TodoCreateRequest;
import com.sprint.dailyreceipt.domain.todo.dao.TodoRepository;
import com.sprint.dailyreceipt.domain.todo.entity.Todo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@DisplayName("TodoCreateService Unit Test")
class TodoCreateServiceTest {

    TodoRepository todoRepository;

    TodoCreateService todoCreateService;

    @BeforeEach
    void init() {
        todoRepository = mock(TodoRepository.class);

        todoCreateService = new TodoCreateService(todoRepository);
    }

    @Test
    @DisplayName("create() : Todo 생성 요청 목록을 통해 Todo를 생성할 수 있다.")
    void testCreate() throws Exception {
        //given
        TodoCreateRequest todoCreateRequest = TodoCreateRequest.builder()
                                                               .date(LocalDate.now())
                                                               .isDone(true)
                                                               .timer("05:07")
                                                               .task("TDD 공부")
                                                               .build();

        Account account = Account.builder()
                                 .nickname("user1")
                                 .build();

        Todo todo = Todo.builder()
                        .id(1L)
                        .account(account)
                        .date(todoCreateRequest.getDate())
                        .isDone(todoCreateRequest.isDone())
                        .timer(todoCreateRequest.getTimer())
                        .task(todoCreateRequest.getTask())
                        .build();

        account.addTodo(todo);

        //when
        when(todoRepository.save(any()))
                .thenReturn(todo);

        Long savedId = todoCreateService.create(account, todoCreateRequest);

        //then
        assertThat(savedId).isEqualTo(todo.getId());
        assertThat(account.getTodos().get(0).getId()).isEqualTo(todo.getId());
    }
}