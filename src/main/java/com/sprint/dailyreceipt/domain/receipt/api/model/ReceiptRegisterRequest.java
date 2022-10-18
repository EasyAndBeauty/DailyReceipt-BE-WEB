package com.sprint.dailyreceipt.domain.receipt.api.model;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ReceiptRegisterRequest {

    private List<Integer> todoIds;

    private boolean pinned;

    private String famousSaying;

    private String name;

    @Builder
    public ReceiptRegisterRequest(List<Integer> todoIds, boolean pinned, String famousSaying, String name) {
        this.todoIds = todoIds;
        this.pinned = pinned;
        this.famousSaying = famousSaying;
        this.name = name;
    }
}
