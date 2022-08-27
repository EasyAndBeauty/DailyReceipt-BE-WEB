package com.sprint.dailyreceipt.domain.todo.service;

import com.sprint.dailyreceipt.domain.todo.entity.Todo;
import com.sprint.dailyreceipt.domain.todo.repository.TodoRepository;
import com.sprint.dailyreceipt.web.model.TodoCreateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;

@Service
@RequiredArgsConstructor
public class TodoService {

    private final TodoRepository todoRepository;

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
}
