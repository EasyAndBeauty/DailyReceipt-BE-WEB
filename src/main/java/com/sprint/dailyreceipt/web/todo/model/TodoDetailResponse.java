package com.sprint.dailyreceipt.web.todo.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.ZonedDateTime;

@Getter
@Setter
public class TodoDetailResponse {

    private String task;
    private ZonedDateTime createdAt;
    private boolean isDone;

    private String hour;

    private String minute;

    @Builder
    public TodoDetailResponse(String task, ZonedDateTime createdAt, boolean isDone, String timer) {
        this.task = task;
        this.createdAt = createdAt;
        this.isDone = isDone;
        this.hour = String.valueOf(Integer.parseInt(timer) / 60);
        this.minute = String.valueOf(Integer.parseInt(timer) % 60);
    }
}
