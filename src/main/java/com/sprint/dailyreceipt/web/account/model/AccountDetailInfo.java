package com.sprint.dailyreceipt.web.account.model;

import com.sprint.dailyreceipt.domain.account.Account;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AccountDetailInfo {

    private String nickname;

    private String uniqueIdBySocial;

    public AccountDetailInfo(Account account) {
        this.nickname = account.getNickname();
    }
}
