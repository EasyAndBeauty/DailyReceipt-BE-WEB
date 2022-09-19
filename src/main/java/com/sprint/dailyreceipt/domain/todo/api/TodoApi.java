package com.sprint.dailyreceipt.domain.todo.api;

import com.sprint.dailyreceipt.domain.account.entity.Account;
import com.sprint.dailyreceipt.domain.todo.api.model.TodoCreateRequest;
import com.sprint.dailyreceipt.domain.todo.api.model.TodoInfoResponse;
import com.sprint.dailyreceipt.domain.todo.application.TodoCreateService;
import com.sprint.dailyreceipt.domain.todo.application.TodoProfileService;
import com.sprint.dailyreceipt.domain.todo.application.TodoUpdateService;
import com.sprint.dailyreceipt.global.annotation.Login;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class TodoApi {

    private final TodoCreateService todoCreateService;

    private final TodoProfileService todoProfileService;

    private final TodoUpdateService todoUpdateService;

    @PostMapping("/v1/todo")
    public ResponseEntity<Long> makeTodo(@Login Account account, @RequestBody TodoCreateRequest todoCreateRequest) {
        return new ResponseEntity<>(todoCreateService.create(account, todoCreateRequest), HttpStatus.CREATED);
    }

    @GetMapping("/v1/todo")
    public List<TodoInfoResponse> searchTodo(@Login Account account,
                                             @RequestParam(required = false) String targetDate) {
        return todoProfileService.findTodoList(account, targetDate);
    }

    @PutMapping("/v1/todo/{todo-id}")
    public TodoInfoResponse updateTodo(@Login Account account,
                                       @PathVariable("todo-id") long todoId,
                                       @RequestBody TodoCreateRequest todoUpdateRequest) {
        return todoUpdateService.update(account, todoId, todoUpdateRequest);
    }
}
