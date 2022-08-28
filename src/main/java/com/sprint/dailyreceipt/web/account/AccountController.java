package com.sprint.dailyreceipt.web.account;

import com.sprint.dailyreceipt.domain.account.service.AccountService;
import com.sprint.dailyreceipt.web.account.model.AccountDetailInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class AccountController {

    private final AccountService accountService;

    @GetMapping("/v1/user/{user-id}")
    public AccountDetailInfo searchUserInfo(@PathVariable("user-id") String socialId) {
        return accountService.getUserDetailInfo(socialId);
    }
}
