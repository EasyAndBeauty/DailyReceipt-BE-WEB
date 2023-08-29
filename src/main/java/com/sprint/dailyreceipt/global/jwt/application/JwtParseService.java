//package com.sprint.dailyreceipt.global.jwt.application;
//
//import com.sprint.dailyreceipt.global.jwt.exception.ExpiredJwtTokenException;
//import com.sprint.dailyreceipt.global.jwt.exception.InvalidJwtTokenException;
//import io.jsonwebtoken.Claims;
//import io.jsonwebtoken.ExpiredJwtException;
//import io.jsonwebtoken.Jwts;
//import io.jsonwebtoken.io.Decoders;
//import io.jsonwebtoken.security.Keys;
//import lombok.RequiredArgsConstructor;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Component;
//
//import java.security.Key;
//
//@Component
//@RequiredArgsConstructor
//public class JwtParseService {
//
//    private final JwtSupport jwtSupport;
//
//    public Claims parseClaims(String token) {
//        try {
//            return Jwts.parserBuilder()
//                       .setSigningKey(jwtSupport.getKey())
//                       .build()
//                       .parseClaimsJws(token)
//                       .getBody();
//        } catch (ExpiredJwtException e) {
//            throw new ExpiredJwtTokenException();
//        }
//    }
//
//    public String getSubject(String token) {
//        try {
//            return Jwts.parserBuilder()
//                       .setSigningKey(jwtSupport.getKey())
//                       .build()
//                       .parseClaimsJws(token)
//                       .getBody()
//                       .getSubject();
//        } catch (ExpiredJwtException e) {
//            throw new ExpiredJwtTokenException();
//        }
//    }
//}
