package com.sprint.dailyreceipt.domain.todo.service;

import com.sprint.dailyreceipt.domain.account.Account;
import com.sprint.dailyreceipt.domain.account.repository.AccountRepository;
import com.sprint.dailyreceipt.domain.todo.entity.Todo;
import com.sprint.dailyreceipt.domain.todo.repository.TodoRepository;
import com.sprint.dailyreceipt.web.todo.model.TodoCreateRequest;
import com.sprint.dailyreceipt.web.todo.model.TodoDetailResponse;
import com.sprint.dailyreceipt.web.todo.model.TodoFindRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.time.Month;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class TodoService {

    private final TodoRepository todoRepository;

    private final AccountRepository accountRepository;

    public long save(TodoCreateRequest request) {
        Todo todo = Todo.builder()
                        .task(request.getTask())
                        .timer(request.getTimer())
                        .isDone(request.isDone())
                        .createdAt(ZonedDateTime.now())
                        .updatedAt(ZonedDateTime.now())
                        .build();

        Todo savedTodo = todoRepository.save(todo);

        return savedTodo.getId();
    }

    public TodoCreateRequest update(TodoCreateRequest updatedRequest, long todoId) {
        Todo savedTodo = todoRepository.findById(todoId)
                                       .orElseThrow(EntityNotFoundException::new);

        Todo updatedTodo = savedTodo.update(updatedRequest.toEntity());

        return new TodoCreateRequest(updatedTodo.getTask(), updatedTodo.getTimer(), updatedTodo.isDone());
    }

    public long save(TodoCreateRequest request, String accountSocialId) {
        Account savedAccount = accountRepository.findByUniqueIdBySocial(accountSocialId)
                                                .orElseThrow(EntityNotFoundException::new);

        Todo todo = Todo.builder()
                        .account(savedAccount)
                        .task(request.getTask())
                        .timer(request.getTimer())
                        .isDone(request.isDone())
                        .createdAt(ZonedDateTime.now())
                        .updatedAt(ZonedDateTime.now())
                        .build();

        savedAccount.addTodo(todo);

        Todo savedTodo = todoRepository.save(todo);

        return savedTodo.getId();
    }

    public List<TodoDetailResponse> find(TodoFindRequest request, String accountSocialId) {
        List<Todo> todos = todoRepository.findTodoBySocialId(accountSocialId);

        int year = request.getDate().getYear();
        int month = request.getDate().getMonth().getValue();
        int day = request.getDate().getDayOfMonth();

        return todos.stream()
                    .filter(todo -> todo.getCreatedAt().getMonth().getValue() == month)
                    .filter(todo -> todo.getCreatedAt().getYear() == year)
                    .filter(todo -> todo.getCreatedAt().getDayOfMonth() == day)
                    .map(todo -> TodoDetailResponse.builder()
                                                   .task(todo.getTask())
                                                   .createdAt(todo.getCreatedAt())
                                                   .isDone(todo.isDone())
                                                   .timer(todo.getTimer())
                                                   .build(
                                                   ))
                    .collect(Collectors.toList());
    }
}
