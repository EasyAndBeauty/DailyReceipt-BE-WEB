package com.sprint.dailyreceipt.domain.todo.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class TodoUpdateResponse {

    private String task;

    private String timer;

    @JsonProperty(value = "isDone")
    private boolean isDone;

    public TodoUpdateResponse(String task, String timer, boolean isDone) {
        this.task = task;
        this.timer = timer;
        this.isDone = isDone;
    }
}
