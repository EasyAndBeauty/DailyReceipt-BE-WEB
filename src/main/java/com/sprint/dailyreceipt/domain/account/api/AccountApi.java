package com.sprint.dailyreceipt.domain.account.api;

import com.sprint.dailyreceipt.domain.account.application.AccountSignInService;
import com.sprint.dailyreceipt.domain.account.api.model.AccountInfoResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class AccountApi {

    private final AccountSignInService accountSignInService;

    @GetMapping("/v1/user/{user-id}")
    public AccountInfoResponse searchUserInfo(@PathVariable("user-id") String socialId) {
        return null;
    }
}
