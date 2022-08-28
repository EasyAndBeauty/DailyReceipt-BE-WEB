package com.sprint.dailyreceipt.web.todo.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.ZonedDateTime;

@Getter
@Setter
public class TodoDetailResponse {

    private String task;

    private String date;

    @JsonProperty(value = "isDone")
    private boolean isDone;

    private String hour;

    private String minute;

    @Builder
    public TodoDetailResponse(String task, String date, boolean isDone, String timer) {
        this.task = task;
        this.date = date;
        this.isDone = isDone;
        this.hour = String.valueOf(Integer.parseInt(timer) / 60);
        this.minute = String.valueOf(Integer.parseInt(timer) % 60);
    }
}
