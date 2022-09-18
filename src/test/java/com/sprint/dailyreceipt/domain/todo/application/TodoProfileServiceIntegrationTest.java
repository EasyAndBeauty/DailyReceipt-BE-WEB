package com.sprint.dailyreceipt.domain.todo.application;

import com.sprint.dailyreceipt.domain.account.entity.Account;
import com.sprint.dailyreceipt.domain.todo.api.model.TodoInfoResponse;
import com.sprint.dailyreceipt.domain.todo.dao.TodoDao;
import com.sprint.dailyreceipt.domain.todo.dao.TodoRepository;
import com.sprint.dailyreceipt.domain.todo.entity.Todo;
import com.sprint.dailyreceipt.support.AbstractIntegrationTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("TodoProfileService Integration Test")
public class TodoProfileServiceIntegrationTest extends AbstractIntegrationTest {

    @Autowired
    TodoProfileService todoProfileService;

    @Autowired
    TodoDao todoDao;

    @Autowired
    TodoRepository todoRepository;

    @Test
    @DisplayName("findTodoList() : 날짜가 주어질 경우, 해당 날짜에 있는 TodoList를 반환할 수 있다.")
    void testFindTodoListWithDate() throws Exception {
        String targetDate = "2022-02-04";

        Account account = testAccount();

        Todo todo1 = Todo.builder()
                         .date(LocalDate.of(2022, 2, 4))
                         .account(account)
                         .isDone(true)
                         .timer("06:06")
                         .task("TDD 공부")
                         .build();

        Todo todo2 = Todo.builder()
                         .date(LocalDate.of(2022, 2, 3))
                         .isDone(true)
                         .account(account)
                         .timer("06:08")
                         .task("ATDD 공부")
                         .build();

        List<Todo> todoList = List.of(todo1, todo2);

        todoRepository.saveAll(todoList);

        //when
        List<TodoInfoResponse> todoListDto = todoProfileService.findTodoList(account, targetDate);

        //then
        assertEquals(todoListDto.size(), 1);
    }

    @Test
    @DisplayName("findTodoList() : 날짜가 주어지지 않을 경우, 사용자가 생성한 todo List 를 모두 조회할 수 있다")
    void testFindTodoListWithoutDate() throws Exception {
        Account account = testAccount();

        Todo todo1 = Todo.builder()
                         .date(LocalDate.of(2022, 2, 4))
                         .account(account)
                         .isDone(true)
                         .timer("06:06")
                         .task("TDD 공부")
                         .build();

        Todo todo2 = Todo.builder()
                         .date(LocalDate.of(2022, 2, 3))
                         .isDone(true)
                         .account(account)
                         .timer("06:08")
                         .task("ATDD 공부")
                         .build();

        List<Todo> todoList = List.of(todo1, todo2);

        todoRepository.saveAll(todoList);

        //when
        List<TodoInfoResponse> todoListDto = todoProfileService.findTodoList(account, null);

        //then
        assertEquals(todoListDto.size(), 4);
    }
}
