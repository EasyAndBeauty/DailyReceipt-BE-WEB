package com.sprint.dailyreceipt.domain.token.application;

import com.sprint.dailyreceipt.domain.account.entity.Account;
import com.sprint.dailyreceipt.domain.token.entity.Token;
import com.sprint.dailyreceipt.global.jwt.application.JwtParseService;
import com.sprint.dailyreceipt.global.jwt.exception.NotHaveTokenException;
import com.sprint.dailyreceipt.global.jwt.application.JwtCreateService;
import com.sprint.dailyreceipt.domain.token.api.model.TokenResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TokenReIssueService {

    private final JwtCreateService jwtCreateService;

    private final JwtParseService jwtParseService;

    public TokenResponse reIssue(Account account) {
        if (hasNotToken(account)) {
            throw new NotHaveTokenException();
        }

        Token token = account.getToken();

        String uniqueIdBySocial = jwtParseService.getSubject(token.getRefreshToken());

        String accessToken = jwtCreateService.createAccessToken(uniqueIdBySocial);
        String refreshToken = jwtCreateService.createRefreshToken(uniqueIdBySocial);

        token.exchangeRefreshToken(refreshToken);

        return new TokenResponse(accessToken, refreshToken);
    }

    private boolean hasNotToken(Account account) {
        return account.getToken() == null;
    }
}
