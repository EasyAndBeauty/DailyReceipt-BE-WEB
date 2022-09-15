package com.sprint.dailyreceipt.domain.account.api.model;

import com.sprint.dailyreceipt.domain.account.entity.Account;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AccountInfoResponse {

    private String nickname;

    private String uniqueIdBySocial;

    public AccountInfoResponse(Account account) {
        this.nickname = account.getNickname();
    }
}
