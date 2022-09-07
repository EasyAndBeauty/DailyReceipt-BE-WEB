package com.sprint.dailyreceipt.global.jwt;

import com.sprint.dailyreceipt.domain.token.entity.TokenType;
import com.sprint.dailyreceipt.global.exception.jwt.ExpiredJwtTokenException;
import com.sprint.dailyreceipt.global.exception.jwt.InvalidJwtTokenException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

import static com.sprint.dailyreceipt.global.ReceiptConstants.*;

@Component
public class JwtService {

    private final long accessTime;

    private final long refreshTime;

    private final String headerType;

    private final Key key;

    private final String issuer;

    public JwtService(@Value("${jwt.token.header-type}") String headerType,
                      @Value("${jwt.token.issuer}") String issuer,
                      @Value("${jwt.token.secret}") String secret,
                      @Value("${jwt.token.access-time}") long accessTime,
                      @Value("${jwt.token.refresh-time}") long refreshTime) {
        byte[] decodeBytes = Decoders.BASE64.decode(secret);
        this.key = Keys.hmacShaKeyFor(decodeBytes);
        this.accessTime = accessTime;
        this.refreshTime = refreshTime;
        this.headerType = headerType;
        this.issuer = issuer;
    }

    public String createAccessToken(String uniqueIdBySocial) {
        long now = new Date().getTime();
        Date issuedAt = new Date();
        Date expireTime = new Date(now + accessTime);

        return Jwts.builder()
                   .signWith(key, SignatureAlgorithm.HS512)
                   .setHeaderParam(JWT_HEADER_PARAM_TYPE, headerType)
                   .setIssuer(issuer)
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
        Date expireTime = new Date(now + refreshTime);

        return Jwts.builder()
                   .signWith(key, SignatureAlgorithm.HS512)
                   .setHeaderParam(JWT_HEADER_PARAM_TYPE, headerType)
                   .setIssuer(issuer)
                   .setSubject(uniqueIdBySocial)
                   .setAudience(TokenType.REFRESH.toString())
                   .setExpiration(expireTime)
                   .setIssuedAt(issuedAt)
                   .claim(AUTHORITIES_KEY, ROLE)
                   .compact();
    }

    public Claims parseClaims(String token) {
        try {
            return Jwts.parserBuilder()
                       .setSigningKey(key)
                       .build()
                       .parseClaimsJws(token)
                       .getBody();
        } catch (ExpiredJwtException e) {
            throw new ExpiredJwtTokenException();
        }
    }

    public String getSubject(String token) {
        try {
            return Jwts.parserBuilder()
                       .setSigningKey(key)
                       .build()
                       .parseClaimsJws(token)
                       .getBody()
                       .getSubject();
        } catch (ExpiredJwtException e) {
            throw new ExpiredJwtTokenException();
        }
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
