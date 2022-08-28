package com.sprint.dailyreceipt.web.account.model;

import com.sprint.dailyreceipt.domain.account.Account;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AccountDetailInfo {

    private String email;

    private String nickname;

    private String uniqueIdBySocial;

    public AccountDetailInfo(Account account) {
        this.email = account.getEmail();
        this.nickname = account.getNickname();
        this.uniqueIdBySocial = account.getUniqueIdBySocial();
    }
}
