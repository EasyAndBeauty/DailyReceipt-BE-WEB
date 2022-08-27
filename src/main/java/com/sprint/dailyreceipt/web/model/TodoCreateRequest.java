package com.sprint.dailyreceipt.web.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
}
