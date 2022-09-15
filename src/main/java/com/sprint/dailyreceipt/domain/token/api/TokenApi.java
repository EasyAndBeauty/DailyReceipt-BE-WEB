package com.sprint.dailyreceipt.domain.token.api;

import com.sprint.dailyreceipt.domain.account.entity.Account;
import com.sprint.dailyreceipt.domain.token.application.TokenReIssueService;
import com.sprint.dailyreceipt.global.annotation.Login;
import com.sprint.dailyreceipt.domain.token.api.model.TokenResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class TokenApi {

    private final TokenReIssueService tokenReIssueService;

    @PostMapping("/v1/tokens/re-issuance")
    public TokenResponse reIssuance(@Login Account account) {
        return tokenReIssueService.reIssue(account);
    }

}
