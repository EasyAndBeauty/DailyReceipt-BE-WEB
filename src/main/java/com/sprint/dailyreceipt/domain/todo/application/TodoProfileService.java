package com.sprint.dailyreceipt.domain.todo.application;

import com.sprint.dailyreceipt.domain.account.entity.Account;
import com.sprint.dailyreceipt.domain.todo.api.model.TodoInfoResponse;
import com.sprint.dailyreceipt.domain.todo.dao.TodoDao;
import com.sprint.dailyreceipt.domain.todo.entity.Todo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class TodoProfileService {

    private final TodoDao todoDao;

    public List<TodoInfoResponse> findTodoList(Account account, String targetDate) {
        Stream<Todo> todoListStream = todoDao.findTodosByAccountId(account.getId())
                                             .stream();

        if (targetDate != null) {
            todoListStream = todoListStream.filter(todo -> todo.getDate().toString().equals(targetDate));
        }

        return todoListStream.map(todo -> TodoInfoResponse.builder()
                                                          .task(todo.getTask())
                                                          .date(todo.getDate().toString())
                                                          .isDone(todo.isDone())
                                                          .timer(todo.getTimer())
                                                          .todoId(todo.getId())
                                                          .build())
                             .collect(Collectors.toList());
    }

    public List<TodoInfoResponse> findTodoListByTodoIds(List<Long> todoIds) {
        return todoDao.findTodosByTodoIds(todoIds)
                      .stream()
                      .map(todo -> TodoInfoResponse.builder()
                                                   .task(todo.getTask())
                                                   .date(todo.getDate().toString())
                                                   .isDone(todo.isDone())
                                                   .timer(todo.getTimer())
                                                   .todoId(todo.getId())
                                                   .build())
                      .collect(Collectors.toList());
    }
}
