package com.sprint.dailyreceipt.global.jwt;

import com.sprint.dailyreceipt.domain.token.entity.TokenType;
import com.sprint.dailyreceipt.global.exception.jwt.ExpiredJwtTokenException;
import com.sprint.dailyreceipt.global.exception.jwt.InvalidJwtTokenException;
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

@DisplayName("JwtService Integration Test")
class JwtServiceTest extends AbstractIntegrationTest {

    @Autowired
    private JwtService jwtService;

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

    @Test
    @DisplayName("parseClaims() : 토큰의 만료 기간이 지날 경우 ExpiredJwtTokenException 을 던진다")
    void test_parseClaims_throw_ExpiredJwtTokenException() throws Exception {
        //when
        String testToken = createTestToken(secret, new Date(1));

        //then
        assertThrows(ExpiredJwtTokenException.class,
                     () -> {
                         jwtService.parseClaims(testToken);
                     });
    }

    @Test
    @DisplayName("validateToken() : secret key가 잘못될 경우 InvalidJwtTokenException 을 던진다")
    void test_validateToken_throw_InvalidJwtTokenException() throws Exception {
        //when
        String testToken = createTestToken("asdfasdjfkhasdlkjhavcnmxklzxcveqwrilqhewbvn" +
                                                   "asdflkasdfklajsdasdfafasdfsadffkljasdasdfjklqewr", new Date(1234));

        //then
        assertThrows(InvalidJwtTokenException.class,
                     () -> {
                         jwtService.validateToken(testToken);
                     });
    }
}