package com.sprint.dailyreceipt.domain.token.service;

import com.sprint.dailyreceipt.domain.account.Account;
import com.sprint.dailyreceipt.domain.token.entity.Token;
import com.sprint.dailyreceipt.global.exception.jwt.NotHaveTokenException;
import com.sprint.dailyreceipt.global.jwt.JwtService;
import com.sprint.dailyreceipt.web.token.model.TokenResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TokenService {

    private final JwtService jwtService;

    public TokenResponse reIssue(Account account) {
        if (hasNotToken(account)) {
            throw new NotHaveTokenException();
        }

        Token token = account.getToken();

        String uniqueIdBySocial = jwtService.getSubject(token.getRefreshToken());

        String accessToken = jwtService.createAccessToken(uniqueIdBySocial);
        String refreshToken = jwtService.createRefreshToken(uniqueIdBySocial);

        token.exchangeRefreshToken(refreshToken);

        return new TokenResponse(accessToken, refreshToken);
    }

    private boolean hasNotToken(Account account) {
        return account.getToken() == null;
    }
}
