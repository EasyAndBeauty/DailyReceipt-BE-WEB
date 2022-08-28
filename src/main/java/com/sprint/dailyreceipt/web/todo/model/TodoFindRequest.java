package com.sprint.dailyreceipt.web.todo.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class TodoFindRequest {

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;
}
