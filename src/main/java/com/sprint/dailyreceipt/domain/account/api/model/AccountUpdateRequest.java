package com.sprint.dailyreceipt.domain.account.api.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AccountUpdateRequest {

    private String nickname;

    public AccountUpdateRequest(String nickname) {
        this.nickname = nickname;
    }
}
