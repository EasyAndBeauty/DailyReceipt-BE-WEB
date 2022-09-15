package com.sprint.dailyreceipt.domain.account.application;

import com.sprint.dailyreceipt.domain.account.entity.Account;
import com.sprint.dailyreceipt.domain.account.dao.AccountRepository;
import com.sprint.dailyreceipt.domain.token.api.model.TokenResponse;
import com.sprint.dailyreceipt.domain.token.entity.Token;
import com.sprint.dailyreceipt.domain.token.dao.TokenRepository;
import com.sprint.dailyreceipt.global.jwt.application.JwtCreateService;
import com.sprint.dailyreceipt.infra.kakao.model.KakaoProfileResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class AccountSignInService {

    private final AccountRepository accountRepository;

    private final JwtCreateService jwtCreateService;

    private final TokenRepository tokenRepository;

    public TokenResponse signIn(KakaoProfileResponse kakaoProfileResponse) {
        String uniqueIdBySocial = kakaoProfileResponse.getId();

        String accessToken = jwtCreateService.createAccessToken(uniqueIdBySocial);
        String refreshToken = jwtCreateService.createRefreshToken(uniqueIdBySocial);

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
