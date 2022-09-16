package com.sprint.dailyreceipt.domain.todo.api;

import com.sprint.dailyreceipt.domain.account.entity.Account;
import com.sprint.dailyreceipt.domain.todo.api.model.TodoCreateRequest;
import com.sprint.dailyreceipt.domain.todo.application.TodoCreateService;
import com.sprint.dailyreceipt.global.annotation.Login;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class TodoApi {

    private final TodoCreateService todoCreateService;

    @PostMapping("/v1/todo")
    public ResponseEntity<Long> makeTodo(@Login Account account, @RequestBody TodoCreateRequest todoCreateRequest) {
        return new ResponseEntity<>(todoCreateService.create(account, todoCreateRequest), HttpStatus.CREATED);
    }
}
