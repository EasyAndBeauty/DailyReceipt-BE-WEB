package com.sprint.dailyreceipt.global.jwt.application;

import com.sprint.dailyreceipt.domain.token.entity.TokenType;
import com.sprint.dailyreceipt.support.AbstractIntegrationTest;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.security.Key;
import java.util.Date;

import static com.sprint.dailyreceipt.global.ReceiptConstants.AUTHORITIES_KEY;
import static com.sprint.dailyreceipt.global.ReceiptConstants.JWT_HEADER_PARAM_TYPE;
import static com.sprint.dailyreceipt.global.ReceiptConstants.ROLE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("JwtCreateService Integration Test")
class JwtCreateServiceTest extends AbstractIntegrationTest {

    @Autowired
    private JwtCreateService jwtCreateService;

    @Autowired
    private JwtParseService jwtParseService;

    @Value("${jwt.token.secret}")
    String secret;

    final String UNIQUE_ID = "uniqueId";

    String createTestToken(String secret, Date expireTime) {
        Key key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret));

        return Jwts.builder()
                   .signWith(key, SignatureAlgorithm.HS512)
                   .setHeaderParam(JWT_HEADER_PARAM_TYPE, "JWT")
                   .setIssuer("receipt")
                   .setSubject(UNIQUE_ID)
                   .setAudience(TokenType.ACCESS.toString())
                   .setExpiration(expireTime)
                   .setIssuedAt(new Date())
                   .claim(AUTHORITIES_KEY, ROLE)
                   .compact();
    }

    @Test
    @DisplayName("createAccessToken() : 사용자 아이디를 통해 access token을 생성할 수 있다")
    void test_createAccessToken() throws Exception {
        //when
        String accessToken = jwtCreateService.createAccessToken(UNIQUE_ID);
        String subject = jwtParseService.getSubject(accessToken);
        Claims claims = jwtParseService.parseClaims(accessToken);

        //then
        assertThat(subject).isEqualTo(UNIQUE_ID);
        assertThat(claims.getAudience()).isEqualTo(TokenType.ACCESS.toString());
    }

    @Test
    @DisplayName("createRefreshToken() : 사용자 아이디를 통해 refresh token을 생성할 수 있다")
    void test_createRefreshToken() throws Exception {
        //when
        String refreshToken = jwtCreateService.createRefreshToken(UNIQUE_ID);
        String subject = jwtParseService.getSubject(refreshToken);
        Claims claims = jwtParseService.parseClaims(refreshToken);

        //then
        assertThat(subject).isEqualTo(UNIQUE_ID);
        assertThat(claims.getAudience()).isEqualTo(TokenType.REFRESH.toString());
    }


}