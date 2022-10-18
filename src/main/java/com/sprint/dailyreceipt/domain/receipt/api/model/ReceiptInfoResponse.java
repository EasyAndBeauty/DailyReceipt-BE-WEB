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

    private ReceiptInfoResponse(List<Integer> todoIds, String famousSaying, String name) {
        this.todoIds = todoIds;
        this.famousSaying = famousSaying;
        this.name = name;
    }

    public static ReceiptInfoResponse of(List<Integer> todoIds, String famousSaying, String name) {
        return new ReceiptInfoResponse(todoIds, famousSaying, name);
    }
}
