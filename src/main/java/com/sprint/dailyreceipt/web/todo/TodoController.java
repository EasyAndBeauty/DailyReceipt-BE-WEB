package com.sprint.dailyreceipt.web.todo;

import com.sprint.dailyreceipt.domain.todo.service.TodoService;
import com.sprint.dailyreceipt.web.todo.model.TodoCreateRequest;
import com.sprint.dailyreceipt.web.todo.model.TodoDetailResponse;
import com.sprint.dailyreceipt.web.todo.model.TodoFindRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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

    @PostMapping("/v2/todo/{account-id}")
    public long makeTodoV2(@RequestBody TodoCreateRequest request, @PathVariable("account-id") String accountSocialId) {
        return todoService.save(request, accountSocialId);
    }

    @GetMapping("/v2/todo/{account-id}")
    public List<TodoDetailResponse> findTodoListV2(@RequestBody TodoFindRequest request,
                                                 @PathVariable("account-id") String accountSocialId) {
        return todoService.find(request, accountSocialId);
    }
}
