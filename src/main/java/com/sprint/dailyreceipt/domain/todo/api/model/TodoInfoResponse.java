package com.sprint.dailyreceipt.domain.todo.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class TodoInfoResponse {

    private String task;

    private String date;

    @JsonProperty(value = "isDone")
    private boolean isDone;

    private String timer;

    private long todoId;

    @Builder
    public TodoInfoResponse(String task, String date, boolean isDone, String timer, long todoId) {
        this.task = task;
        this.date = date;
        this.isDone = isDone;
        this.timer = timer;
        this.todoId = todoId;
    }
}
