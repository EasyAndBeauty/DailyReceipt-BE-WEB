package com.sprint.dailyreceipt.domain.todo.dao;

import com.sprint.dailyreceipt.domain.todo.entity.Todo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class TodoDao {

    private final TodoRepository todoRepository;

    public List<Todo> findTodosByAccountId(long accountId) {
        return todoRepository.findTodosByAccountId(accountId);
    }
}
