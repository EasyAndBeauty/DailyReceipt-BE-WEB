package com.sprint.dailyreceipt.global.jwt;

import com.sprint.dailyreceipt.domain.token.entity.TokenType;
import com.sprint.dailyreceipt.support.AbstractIntegrationTest;
import io.jsonwebtoken.Claims;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("JwtService Integration Test")
class JwtServiceTest extends AbstractIntegrationTest {

    @Autowired
    private JwtService jwtService;

    @Value("${jwt.token.secret}")
    String secret;

    final String UNIQUE_ID = "uniqueId";

    @Test
    @DisplayName("createAccessToken() : 사용자 아이디를 통해 access token을 생성할 수 있다")
    void test_createAccessToken() throws Exception {
        //when
        String accessToken = jwtService.createAccessToken(UNIQUE_ID);
        String subject = jwtService.getSubject(accessToken);
        Claims claims = jwtService.parseClaims(accessToken);

        //then
        assertThat(subject).isEqualTo(UNIQUE_ID);
        assertThat(claims.getAudience()).isEqualTo(TokenType.ACCESS.toString());
    }

    @Test
    @DisplayName("createRefreshToken() : 사용자 아이디를 통해 refresh token을 생성할 수 있다")
    void test_createRefreshToken() throws Exception {
        //when
        String refreshToken = jwtService.createRefreshToken(UNIQUE_ID);
        String subject = jwtService.getSubject(refreshToken);
        Claims claims = jwtService.parseClaims(refreshToken);

        //then
        assertThat(subject).isEqualTo(UNIQUE_ID);
        assertThat(claims.getAudience()).isEqualTo(TokenType.REFRESH.toString());
    }

    @Test
    @DisplayName("parseClaims() : 토큰을 통해 토큰의 정보들을 가져올 수 있다")
    void test_parseClaims() throws Exception {
        //given
        String accessToken = jwtService.createAccessToken(UNIQUE_ID);

        //when
        Claims claims = jwtService.parseClaims(accessToken);

        //then
        assertThat(claims.getSubject()).isEqualTo(UNIQUE_ID);
        assertThat(claims.getIssuer()).isEqualTo("receipt");
        assertThat(claims.getAudience()).isEqualTo(TokenType.ACCESS.toString());
    }

    @Test
    @DisplayName("getSubject() : 토큰을 통해 유저를 식별하는 sub claim을 가져올 수 있다")
    void test_getSubject() throws Exception {
        //given
        String accessToken = jwtService.createAccessToken(UNIQUE_ID);

        //when
        Claims claims = jwtService.parseClaims(accessToken);

        //then
        assertThat(claims.getSubject()).isEqualTo(UNIQUE_ID);
    }
}