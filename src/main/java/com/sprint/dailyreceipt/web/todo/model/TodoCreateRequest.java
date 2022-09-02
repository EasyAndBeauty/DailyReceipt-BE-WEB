package com.sprint.dailyreceipt.web.todo.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.sprint.dailyreceipt.domain.todo.entity.Todo;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.ZonedDateTime;

@Getter
@Setter
@NoArgsConstructor
public class TodoCreateRequest {

    private String task;

    private String timer;

    @JsonProperty(value = "isDone")
    private boolean isDone;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;

    @Builder
    public TodoCreateRequest(String task, String timer, boolean isDone, LocalDate date) {
        this.task = task;
        this.timer = timer;
        this.isDone = isDone;
        this.date = date;
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
