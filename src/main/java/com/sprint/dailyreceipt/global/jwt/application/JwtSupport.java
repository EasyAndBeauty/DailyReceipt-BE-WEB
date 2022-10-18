package com.sprint.dailyreceipt.global.jwt.application;

import com.sprint.dailyreceipt.global.jwt.exception.ExpiredJwtTokenException;
import com.sprint.dailyreceipt.global.jwt.exception.InvalidJwtTokenException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;

@Component
public class JwtSupport {

    private final Key key;

    public JwtSupport(@Value("${jwt.token.secret}") String secret) {
        byte[] decodeBytes = Decoders.BASE64.decode(secret);
        this.key = Keys.hmacShaKeyFor(decodeBytes);
    }

    public Key getKey() {
        return key;
    }

    public void validateToken(String jwt) {
        try {
            Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(jwt);
        } catch (ExpiredJwtTokenException e) {
            throw new ExpiredJwtTokenException();
        } catch (Exception e) {
            throw new InvalidJwtTokenException();
        }
    }
}
