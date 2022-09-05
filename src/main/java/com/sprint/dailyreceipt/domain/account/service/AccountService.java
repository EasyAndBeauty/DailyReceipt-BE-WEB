package com.sprint.dailyreceipt.domain.account.service;

import com.sprint.dailyreceipt.domain.account.Account;
import com.sprint.dailyreceipt.domain.account.repository.AccountRepository;
import com.sprint.dailyreceipt.domain.token.entity.Token;
import com.sprint.dailyreceipt.domain.token.repository.TokenRepository;
import com.sprint.dailyreceipt.global.jwt.JwtService;
import com.sprint.dailyreceipt.web.oauth.kakao.model.KakaoProfileResponse;
import com.sprint.dailyreceipt.web.token.model.TokenResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class AccountService {

    private final AccountRepository accountRepository;

    private final JwtService jwtService;

    private final TokenRepository tokenRepository;

    public TokenResponse signIn(KakaoProfileResponse kakaoProfileResponse) {
        String uniqueIdBySocial = kakaoProfileResponse.getId();

        String accessToken = jwtService.createAccessToken(uniqueIdBySocial);
        String refreshToken = jwtService.createRefreshToken(uniqueIdBySocial);

        TokenResponse tokenResponse = new TokenResponse(accessToken, refreshToken);

        tokenRepository.findByUniqueIdBySocial(uniqueIdBySocial)
                       .ifPresentOrElse(
                               token -> {
                                   token.exchangeRefreshToken(refreshToken);
                                   tokenResponse.setFirst(false);
                               },
                               () -> {
                                   Token token = Token.builder()
                                                      .refreshToken(refreshToken)
                                                      .uniqueIdBySocial(uniqueIdBySocial)
                                                      .build();

                                   tokenRepository.save(token);

                                   Account createdAccount = Account.of(token);
                                   accountRepository.save(createdAccount);

                                   token.addAccount(createdAccount);
                                   tokenResponse.setFirst(true);
                                   tokenRepository.save(token);
                               }
                       );

        return tokenResponse;
    }
}
