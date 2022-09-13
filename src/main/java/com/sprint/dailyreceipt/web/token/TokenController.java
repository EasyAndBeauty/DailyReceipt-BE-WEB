package com.sprint.dailyreceipt.web.token;

import com.sprint.dailyreceipt.domain.account.Account;
import com.sprint.dailyreceipt.domain.token.service.TokenService;
import com.sprint.dailyreceipt.global.annotation.Login;
import com.sprint.dailyreceipt.web.token.model.TokenResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class TokenController {

    private final TokenService tokenService;

    @PostMapping("/v1/tokens/re-issuance")
    public TokenResponse reIssuance(@Login Account account) {
        return tokenService.reIssue(account);
    }

}
