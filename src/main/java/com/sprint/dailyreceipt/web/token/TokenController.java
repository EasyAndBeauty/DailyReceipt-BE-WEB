package com.sprint.dailyreceipt.web.token;

import com.sprint.dailyreceipt.domain.account.Account;
import com.sprint.dailyreceipt.domain.token.service.TokenService;
import com.sprint.dailyreceipt.web.token.model.TokenResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class TokenController {

    private final TokenService tokenService;

    @PostMapping("/v1/tokens/re-issuance")
    public TokenResponse reIssuance(@RequestBody Account account) {
        System.out.println("account.getToken() = " + account.getToken());
        return tokenService.reIssue(account);
    }

}
