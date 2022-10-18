package com.sprint.dailyreceipt.domain.account.application;

import com.sprint.dailyreceipt.domain.token.api.model.TokenResponse;
import com.sprint.dailyreceipt.domain.token.application.TokenCreateService;
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

    private final AccountSignUpService accountSignUpService;

    private final JwtCreateService jwtCreateService;

    private final TokenCreateService tokenCreateService;

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
                                   Token createdToken = tokenCreateService.create(refreshToken, uniqueIdBySocial);

                                   accountSignUpService.signUp(createdToken);

                                   tokenResponse.setFirst(true);
                               }
                       );

        return tokenResponse;
    }
}
