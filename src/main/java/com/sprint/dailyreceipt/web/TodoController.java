package com.sprint.dailyreceipt.web;

import com.sprint.dailyreceipt.domain.todo.service.TodoService;
import com.sprint.dailyreceipt.web.model.TodoCreateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class TodoController {

    private final TodoService todoService;

    @PostMapping("/v1/todo")
    public long makeTodo(@RequestBody TodoCreateRequest request) {
        return todoService.save(request);
    }

    @PutMapping("/v1/todo/{todo-id}")
    public TodoCreateRequest updateTodo(@RequestBody TodoCreateRequest updatedRequest,
                                        @PathVariable("todo-id") long todoId) {
        return todoService.update(updatedRequest, todoId);
    }
}
