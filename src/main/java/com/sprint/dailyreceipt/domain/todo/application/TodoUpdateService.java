package com.sprint.dailyreceipt.domain.todo.application;

import com.sprint.dailyreceipt.domain.account.entity.Account;
import com.sprint.dailyreceipt.domain.account.exception.AccountMisMatchException;
import com.sprint.dailyreceipt.domain.todo.api.model.TodoCreateRequest;
import com.sprint.dailyreceipt.domain.todo.api.model.TodoInfoResponse;
import com.sprint.dailyreceipt.domain.todo.dao.TodoDao;
import com.sprint.dailyreceipt.domain.todo.entity.Todo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class TodoUpdateService {

    private final TodoDao todoDao;

    public TodoInfoResponse update(Account account, long todoId, TodoCreateRequest todoCreateRequest) {

        Todo savedTodo = todoDao.findTodoById(todoId);

        if (account.isNotSame(savedTodo.getAccount())) {
            throw new AccountMisMatchException();
        }

        Todo updateTodo = Todo.builder()
                              .isDone(todoCreateRequest.isDone())
                              .timer(todoCreateRequest.getTimer())
                              .task(todoCreateRequest.getTask())
                              .build();

        Todo updatedTodo = savedTodo.update(updateTodo);

        return TodoInfoResponse.builder()
                               .todoId(updatedTodo.getId())
                               .date(updatedTodo.getDate().toString())
                               .isDone(updatedTodo.isDone())
                               .task(updatedTodo.getTask())
                               .timer(updatedTodo.getTimer())
                               .build();
    }
}
