package com.sprint.dailyreceipt.global.jwt.application;

import com.sprint.dailyreceipt.domain.token.entity.TokenType;
import com.sprint.dailyreceipt.global.jwt.application.JwtSupport;
import com.sprint.dailyreceipt.global.jwt.exception.InvalidJwtTokenException;
import com.sprint.dailyreceipt.support.AbstractIntegrationTest;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.security.Key;
import java.util.Date;

import static com.sprint.dailyreceipt.global.ReceiptConstants.AUTHORITIES_KEY;
import static com.sprint.dailyreceipt.global.ReceiptConstants.JWT_HEADER_PARAM_TYPE;
import static com.sprint.dailyreceipt.global.ReceiptConstants.ROLE;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName("JwtSupport Integration Test")
public class JwtSupportTest extends AbstractIntegrationTest {

    @Autowired
    JwtSupport jwtSupport;

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
    @DisplayName("validateToken() : secret key가 잘못될 경우 InvalidJwtTokenException 을 던진다")
    void test_validateToken_throw_InvalidJwtTokenException() throws Exception {
        //when
        String testToken = createTestToken("asdfasdjfkhasdlkjhavcnmxklzxcveqwrilqhewbvn" +
                                                   "asdflkasdfklajsdasdfafasdfsadffkljasdasdfjklqewr", new Date(1234));

        //then
        assertThrows(InvalidJwtTokenException.class,
                     () -> {
                         jwtSupport.validateToken(testToken);
                     });
    }
}
