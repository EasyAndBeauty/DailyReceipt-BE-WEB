package com.sprint.dailyreceipt.domain.todo.application;

import com.sprint.dailyreceipt.domain.todo.dao.TodoDao;
import com.sprint.dailyreceipt.domain.todo.dao.TodoRepository;
import com.sprint.dailyreceipt.support.AbstractIntegrationTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityNotFoundException;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("TodoRemoveService Integration Test")
class TodoRemoveServiceIntegrationTest extends AbstractIntegrationTest {

    @Autowired
    TodoRepository todoRepository;

    @Autowired
    TodoRemoveService todoRemoveService;

    @Autowired
    TodoDao todoDao;

    @Test
    @DisplayName("deleteTodo() : Todo 삭제 요청을 통해 Todo 를 삭제할 수 있다")
    void testDeleteTodo() throws Exception {
        //when
        todoRemoveService.deleteTodo(defaultAccount(), 1L);

        //then
        assertThrows(
                EntityNotFoundException.class,
                () -> todoDao.findTodoById(1L)
        );
    }
}