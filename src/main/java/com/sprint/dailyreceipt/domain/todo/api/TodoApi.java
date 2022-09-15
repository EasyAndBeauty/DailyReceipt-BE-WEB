package com.sprint.dailyreceipt.domain.todo.api;

import com.sprint.dailyreceipt.domain.todo.application.TodoService;
import com.sprint.dailyreceipt.domain.todo.api.model.TodoCreateRequest;
import com.sprint.dailyreceipt.domain.todo.api.model.TodoInfoResponse;
import com.sprint.dailyreceipt.domain.todo.api.model.TodoUpdateResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class TodoApi {

    private final TodoService todoService;

    @PostMapping("/v1/todo")
    public long makeTodo(@RequestBody TodoCreateRequest request) {
        return todoService.save(request);
    }

    @PutMapping("/v1/todo/{todo-id}")
    public TodoUpdateResponse updateTodo(@RequestBody TodoCreateRequest updatedRequest,
                                         @PathVariable("todo-id") long todoId) {
        return todoService.update(updatedRequest, todoId);
    }

    @PostMapping("/v2/todo/{account-id}")
    public long makeTodoV2(@RequestBody TodoCreateRequest request, @PathVariable("account-id") String accountSocialId) {
        return todoService.save(request, accountSocialId);
    }

    @GetMapping("/v2/todo/{account-id}")
    public List<TodoInfoResponse> findTodoListV2(
            @RequestParam("date") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date,
            @PathVariable("account-id") String accountSocialId) {

        return todoService.find(date, accountSocialId);
    }

    @DeleteMapping("/v1/todo/{todo-id}")
    public void deleteTodoV1(@PathVariable("todo-id") long todoId) {
        todoService.delete(todoId);
    }
}