package com.sprint.dailyreceipt.domain.account.api;

import com.sprint.dailyreceipt.domain.account.api.model.AccountUpdateRequest;
import com.sprint.dailyreceipt.domain.account.application.AccountProfileService;
import com.sprint.dailyreceipt.domain.account.api.model.AccountInfoResponse;
import com.sprint.dailyreceipt.domain.account.application.AccountUpdateService;
import com.sprint.dailyreceipt.domain.account.entity.Account;
import com.sprint.dailyreceipt.global.annotation.Login;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class AccountApi {

    private final AccountProfileService accountProfileService;

    private final AccountUpdateService accountUpdateService;

    @GetMapping("/v1/user")
    public AccountInfoResponse searchUserInfo(@Login Account account) {
        return accountProfileService.findAccountInfo(account);
    }

    @PutMapping("/v1/user")
    public AccountInfoResponse updateAccount(@Login Account account, @RequestBody AccountUpdateRequest accountUpdateRequest) {
        return accountUpdateService.update(account, accountUpdateRequest);
    }
}
