package com.sprint.dailyreceipt.domain.todo.application;

import com.sprint.dailyreceipt.domain.todo.dao.TodoDao;
import com.sprint.dailyreceipt.domain.todo.entity.Todo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

@DisplayName("TodoProfileService Unit Test")
class TodoProfileServiceTest {

    TodoDao todoDao;

    TodoProfileService todoProfileService;

    @BeforeEach
    void init() {
        todoDao = mock(TodoDao.class);

        todoProfileService = new TodoProfileService(todoDao);
    }

    @Test
    @DisplayName("findTodoList() : 날짜가 주어질 경우, 해당 날짜에 있는 TodoList를 반환할 수 있다.")
    void testFindTodoListWithDate() throws Exception {
        //given
        String targetDate = "2022-02-04";

        Todo todo1 = Todo.builder()
                         .id(1L)
                         .date(LocalDate.of(2022, 2, 4))
                         .isDone(true)
                         .timer("06:06")
                         .task("TDD 공부")
                         .build();

        Todo todo2 = Todo.builder()
                         .id(2L)
                         .date(LocalDate.of(2022, 2, 3))
                         .isDone(true)
                         .timer("06:08")
                         .task("ATDD 공부")
                         .build();

        List<Todo> todoList = List.of(todo1, todo2);

        //when
        List<Todo> result = todoList.stream()
                                    .filter(todo -> {
                                        return todo.getDate().toString().equals(targetDate);
                                    })
                                    .collect(Collectors.toList());

        //then
        assertThat(result.get(0).getId()).isEqualTo(todo1.getId());
        assertEquals(result.size(), 1);
    }
}