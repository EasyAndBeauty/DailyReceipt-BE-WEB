package com.sprint.dailyreceipt.domain.todo.application;

import com.sprint.dailyreceipt.domain.account.entity.Account;
import com.sprint.dailyreceipt.domain.account.dao.AccountRepository;
import com.sprint.dailyreceipt.domain.todo.entity.Todo;
import com.sprint.dailyreceipt.domain.todo.dao.TodoRepository;
import com.sprint.dailyreceipt.domain.todo.api.model.TodoCreateRequest;
import com.sprint.dailyreceipt.domain.todo.api.model.TodoInfoResponse;
import com.sprint.dailyreceipt.domain.todo.api.model.TodoUpdateResponse;
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

    //TODO : 메서드 별로 Service 나누고 클래스명 변경
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
//        Account savedAccount = accountRepository.findAccountByUniqueId(accountSocialId)
//                                                .orElseThrow(EntityNotFoundException::new);
//
//        Todo todo = Todo.builder()
//                        .account(savedAccount)
//                        .task(request.getTask())
//                        .timer(request.getTimer())
//                        .isDone(request.isDone())
//                        .createdAt(ZonedDateTime.now())
//                        .updatedAt(ZonedDateTime.now())
//                        .date(request.getDate().toString())
//                        .build();
//
//        savedAccount.addTodo(todo);
//
//        Todo savedTodo = todoRepository.save(todo);
//
//        return savedTodo.getId();

        return 0L;
    }

    public List<TodoInfoResponse> find(LocalDate date, String accountSocialId) {
        List<Todo> todos = todoRepository.findTodoBySocialId(accountSocialId);

        return todos.stream()
                    .filter(todo -> isSameDate(date, todo.getDate()))
                    .map(todo -> TodoInfoResponse.builder()
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
