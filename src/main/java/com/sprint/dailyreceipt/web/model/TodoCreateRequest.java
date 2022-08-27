package com.sprint.dailyreceipt.web.model;

import com.sprint.dailyreceipt.domain.todo.entity.Todo;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.ZonedDateTime;

@Getter
@Setter
@NoArgsConstructor
public class TodoCreateRequest {

    private String task;

    private String timer;

    private boolean isDone;

    public TodoCreateRequest(String task, String timer, boolean isDone) {
        this.task = task;
        this.timer = timer;
        this.isDone = isDone;
    }

    public Todo toEntity() {
        return Todo.builder()
                   .updatedAt(ZonedDateTime.now())
                   .timer(timer)
                   .task(task)
                   .isDone(isDone)
                   .build();
    }
}
