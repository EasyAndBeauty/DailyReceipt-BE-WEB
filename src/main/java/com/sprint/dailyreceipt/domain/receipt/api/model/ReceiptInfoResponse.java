package com.sprint.dailyreceipt.domain.receipt.api.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ReceiptInfoResponse {

    private List<Integer> todoIds;

    private String famousSaying;

    private String name;

    private long id;

    public ReceiptInfoResponse(List<Integer> todoIds, String famousSaying, String name, long id) {
        this.todoIds = todoIds;
        this.famousSaying = famousSaying;
        this.name = name;
        this.id = id;
    }

    public static ReceiptInfoResponse of(List<Integer> todoIds, String famousSaying, String name, long id) {
        return new ReceiptInfoResponse(todoIds, famousSaying, name, id);
    }
}
