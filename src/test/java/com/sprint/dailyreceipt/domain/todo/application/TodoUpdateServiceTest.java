package com.sprint.dailyreceipt.domain.todo.application;

import com.sprint.dailyreceipt.domain.account.entity.Account;
import com.sprint.dailyreceipt.domain.todo.api.model.TodoCreateRequest;
import com.sprint.dailyreceipt.domain.todo.dao.TodoDao;
import com.sprint.dailyreceipt.domain.todo.entity.Todo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.ZonedDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

@DisplayName("TodoUpdateService Unit Test")
class TodoUpdateServiceTest {

    TodoDao todoDao;

    TodoUpdateService todoUpdateService;

    @BeforeEach
    void init() {
        todoDao = mock(TodoDao.class);

        todoUpdateService = new TodoUpdateService(todoDao);
    }

    @Test
    @DisplayName("update() : Todo 수정 요청을 통해 Todo 를 수정할 수 있다.")
    void testUpdate() throws Exception {
        //given
        TodoCreateRequest todoCreateRequest = TodoCreateRequest.builder()
                                                               .isDone(true)
                                                               .timer("05:07")
                                                               .task("ATDD 공부")
                                                               .build();

        Account account = Account.builder()
                                 .nickname("user1")
                                 .build();

        Todo updateTodo = Todo.builder()
                              .id(1L)
                              .account(account)
                              .updatedAt(ZonedDateTime.now())
                              .isDone(todoCreateRequest.isDone())
                              .timer(todoCreateRequest.getTimer())
                              .task(todoCreateRequest.getTask())
                              .build();

        Todo todo = Todo.builder()
                        .id(1L)
                        .date(LocalDate.of(2022, 2, 4))
                        .isDone(true)
                        .timer("06:06")
                        .task("TDD 공부")
                        .build();

        //when
        Todo updatedTodo = todo.update(updateTodo);

        //then
        assertThat(updatedTodo.getTask()).isEqualTo("ATDD 공부");
        assertTrue(updatedTodo.getUpdatedAt().isAfter(updateTodo.getUpdatedAt()));
    }
}