package com.sprint.dailyreceipt.domain.token.application;

import com.sprint.dailyreceipt.domain.token.dao.TokenRepository;
import com.sprint.dailyreceipt.domain.token.entity.Token;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class TokenCreateService {

    private final TokenRepository tokenRepository;

    public Token create(String refreshToken, String uniqueIdBySocial) {
        Token token = Token.builder()
                           .refreshToken(refreshToken)
                           .uniqueIdBySocial(uniqueIdBySocial)
                           .build();

        return tokenRepository.save(token);
    }
}
