package com.sprint.dailyreceipt.domain.todo.service;

import com.sprint.dailyreceipt.domain.todo.entity.Todo;
import com.sprint.dailyreceipt.domain.todo.repository.TodoRepository;
import com.sprint.dailyreceipt.web.todo.model.TodoCreateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.time.ZonedDateTime;

@Service
@RequiredArgsConstructor
@Transactional
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

    public TodoCreateRequest update(TodoCreateRequest updatedRequest, long todoId) {
        Todo savedTodo = todoRepository.findById(todoId)
                                       .orElseThrow(EntityNotFoundException::new);

        Todo updatedTodo = savedTodo.update(updatedRequest.toEntity());

        return new TodoCreateRequest(updatedTodo.getTask(), updatedTodo.getTimer(), updatedTodo.isDone());
    }
}
