package com.sprint.dailyreceipt.domain.todo.service;

import com.sprint.dailyreceipt.domain.account.Account;
import com.sprint.dailyreceipt.domain.account.repository.AccountRepository;
import com.sprint.dailyreceipt.domain.todo.entity.Todo;
import com.sprint.dailyreceipt.domain.todo.repository.TodoRepository;
import com.sprint.dailyreceipt.web.todo.model.TodoCreateRequest;
import com.sprint.dailyreceipt.web.todo.model.TodoDetailResponse;
import com.sprint.dailyreceipt.web.todo.model.TodoUpdateResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.StringTokenizer;
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

    public TodoUpdateResponse update(TodoCreateRequest updatedRequest, long todoId) {
        Todo savedTodo = todoRepository.findById(todoId)
                                       .orElseThrow(EntityNotFoundException::new);

        Todo updatedTodo = savedTodo.update(updatedRequest.toEntity());

        return new TodoUpdateResponse(updatedTodo.getTask(), updatedTodo.getTimer(), updatedTodo.isDone());
    }

    public long save(TodoCreateRequest request, String accountSocialId) {
        Account savedAccount = accountRepository.findAccountByUniqueId(accountSocialId)
                                                .orElseThrow(EntityNotFoundException::new);

        Todo todo = Todo.builder()
                        .account(savedAccount)
                        .task(request.getTask())
                        .timer(request.getTimer())
                        .isDone(request.isDone())
                        .createdAt(ZonedDateTime.now())
                        .updatedAt(ZonedDateTime.now())
                        .date(request.getDate().toString())
                        .build();

        savedAccount.addTodo(todo);

        Todo savedTodo = todoRepository.save(todo);

        return savedTodo.getId();
    }

    public List<TodoDetailResponse> find(LocalDate date, String accountSocialId) {
        List<Todo> todos = todoRepository.findTodoBySocialId(accountSocialId);

        return todos.stream()
                    .filter(todo -> isSameDate(date, todo.getDate()))
                    .map(todo -> TodoDetailResponse.builder()
                                                   .task(todo.getTask())
                                                   .date(todo.getDate())
                                                   .isDone(todo.isDone())
                                                   .timer(todo.getTimer())
                                                   .todoId(todo.getId())
                                                   .build(
                                                   ))
                    .collect(Collectors.toList());
    }

    private boolean isSameDate(LocalDate date, String todoDate) {
        int targetYear = date.getYear();
        int targetMonth = date.getMonth().getValue();
        int targetDay = date.getDayOfMonth();

        StringTokenizer stringTokenizer = new StringTokenizer(todoDate, "-");

        int year = Integer.parseInt(stringTokenizer.nextToken());
        int month = Integer.parseInt(stringTokenizer.nextToken());
        int day = Integer.parseInt(stringTokenizer.nextToken());

        return targetYear == year && targetMonth == month && targetDay == day;
    }

    public void delete(long todoId) {
        todoRepository.delete(todoRepository.findById(todoId)
                                            .orElseThrow(EntityNotFoundException::new));
    }
}
