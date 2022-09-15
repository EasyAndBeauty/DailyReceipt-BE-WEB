package com.sprint.dailyreceipt.global.jwt.application;

import com.sprint.dailyreceipt.domain.token.entity.TokenType;
import com.sprint.dailyreceipt.global.jwt.exception.ExpiredJwtTokenException;
import com.sprint.dailyreceipt.global.jwt.exception.InvalidJwtTokenException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

import static com.sprint.dailyreceipt.global.ReceiptConstants.*;

@Component
@RequiredArgsConstructor
public class JwtCreateService {

    private final JwtSupport jwtSupport;


    public String createAccessToken(String uniqueIdBySocial) {
        long now = new Date().getTime();
        Date issuedAt = new Date();
        Date expireTime = new Date(now + JWT_TOKEN_ACCESS_TIME);

        return Jwts.builder()
                   .signWith(jwtSupport.getKey(), SignatureAlgorithm.HS512)
                   .setHeaderParam(JWT_HEADER_PARAM_TYPE, JWT_TOKEN_HEADER_TYPE)
                   .setIssuer(JWT_TOKEN_ISSUER)
                   .setSubject(uniqueIdBySocial)
                   .setAudience(TokenType.ACCESS.toString())
                   .setExpiration(expireTime)
                   .setIssuedAt(issuedAt)
                   .claim(AUTHORITIES_KEY, ROLE)
                   .compact();
    }

    public String createRefreshToken(String uniqueIdBySocial) {
        long now = new Date().getTime();
        Date issuedAt = new Date();
        Date expireTime = new Date(now + JWT_TOKEN_REFRESH_TIME);

        return Jwts.builder()
                   .signWith(jwtSupport.getKey(), SignatureAlgorithm.HS512)
                   .setHeaderParam(JWT_HEADER_PARAM_TYPE, JWT_TOKEN_HEADER_TYPE)
                   .setIssuer(JWT_TOKEN_ISSUER)
                   .setSubject(uniqueIdBySocial)
                   .setAudience(TokenType.REFRESH.toString())
                   .setExpiration(expireTime)
                   .setIssuedAt(issuedAt)
                   .claim(AUTHORITIES_KEY, ROLE)
                   .compact();
    }
}
