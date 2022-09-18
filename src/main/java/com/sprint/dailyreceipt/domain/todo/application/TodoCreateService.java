package com.sprint.dailyreceipt.domain.todo.application;

import com.sprint.dailyreceipt.domain.account.entity.Account;
import com.sprint.dailyreceipt.domain.todo.api.model.TodoCreateRequest;
import com.sprint.dailyreceipt.domain.todo.dao.TodoRepository;
import com.sprint.dailyreceipt.domain.todo.entity.Todo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;

@Service
@RequiredArgsConstructor
public class TodoCreateService {

    private final TodoRepository todoRepository;

    public Long create(Account account, TodoCreateRequest todoCreateRequest) {

        Todo todo = Todo.builder()
                        .account(account)
                        .date(todoCreateRequest.getDate())
                        .isDone(todoCreateRequest.isDone())
                        .timer(todoCreateRequest.getTimer())
                        .task(todoCreateRequest.getTask())
                        .createdAt(ZonedDateTime.now())
                        .updatedAt(ZonedDateTime.now())
                        .build();

        account.addTodo(todo);

        Todo savedTodo = todoRepository.save(todo);

        return savedTodo.getId();
    }
}
