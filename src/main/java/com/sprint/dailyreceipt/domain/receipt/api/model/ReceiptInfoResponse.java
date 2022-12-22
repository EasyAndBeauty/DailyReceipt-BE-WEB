package com.sprint.dailyreceipt.domain.receipt.api.model;

import com.sprint.dailyreceipt.domain.todo.api.model.TodoInfoResponse;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ReceiptInfoResponse {

    private List<TodoInfoResponse> todos;

    private String famousSaying;

    private String name;

    private long id;

    public ReceiptInfoResponse(List<TodoInfoResponse> todos, String famousSaying, String name, long id) {
        this.todos = todos;
        this.famousSaying = famousSaying;
        this.name = name;
        this.id = id;
    }

    public static ReceiptInfoResponse of(List<TodoInfoResponse> todos, String famousSaying, String name, long id) {
        return new ReceiptInfoResponse(todos, famousSaying, name, id);
    }
}
