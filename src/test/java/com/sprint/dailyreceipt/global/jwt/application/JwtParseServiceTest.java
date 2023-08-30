//package com.sprint.dailyreceipt.global.jwt.application;
//
//import com.sprint.dailyreceipt.domain.token.entity.TokenType;
//import com.sprint.dailyreceipt.global.jwt.exception.ExpiredJwtTokenException;
//import com.sprint.dailyreceipt.support.AbstractIntegrationTest;
//import io.jsonwebtoken.Claims;
//import io.jsonwebtoken.Jwts;
//import io.jsonwebtoken.SignatureAlgorithm;
//import io.jsonwebtoken.io.Decoders;
//import io.jsonwebtoken.security.Keys;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//
//import java.security.Key;
//import java.util.Date;
//
//import static com.sprint.dailyreceipt.global.ReceiptConstants.AUTHORITIES_KEY;
//import static com.sprint.dailyreceipt.global.ReceiptConstants.JWT_HEADER_PARAM_TYPE;
//import static com.sprint.dailyreceipt.global.ReceiptConstants.ROLE;
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.junit.jupiter.api.Assertions.assertThrows;
//
//@DisplayName("JwtParseService Integration Test")
//public class JwtParseServiceTest extends AbstractIntegrationTest {
//
//    @Autowired
//    private JwtCreateService jwtCreateService;
//
//    @Autowired
//    private JwtParseService jwtParseService;
//
//    @Autowired
//    private JwtSupport jwtSupport;
//
//    @Value("${jwt.token.secret}")
//    String secret;
//
//    final String UNIQUE_ID = "uniqueId";
//
//    String createTestToken(String secret, Date expireTime) {
//        Key key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret));
//
//        return Jwts.builder()
//                   .signWith(key, SignatureAlgorithm.HS512)
//                   .setHeaderParam(JWT_HEADER_PARAM_TYPE, "JWT")
//                   .setIssuer("receipt")
//                   .setSubject(UNIQUE_ID)
//                   .setAudience(TokenType.ACCESS.toString())
//                   .setExpiration(expireTime)
//                   .setIssuedAt(new Date())
//                   .claim(AUTHORITIES_KEY, ROLE)
//                   .compact();
//    }
//
//    @Test
//    @DisplayName("parseClaims() : 토큰을 통해 토큰의 정보들을 가져올 수 있다")
//    void test_parseClaims() throws Exception {
//        //given
//        String accessToken = jwtCreateService.createAccessToken(UNIQUE_ID);
//
//        //when
//        Claims claims = jwtParseService.parseClaims(accessToken);
//
//        //then
//        assertThat(claims.getSubject()).isEqualTo(UNIQUE_ID);
//        assertThat(claims.getIssuer()).isEqualTo("receipt");
//        assertThat(claims.getAudience()).isEqualTo(TokenType.ACCESS.toString());
//    }
//
//    @Test
//    @DisplayName("getSubject() : 토큰을 통해 유저를 식별하는 sub claim을 가져올 수 있다")
//    void test_getSubject() throws Exception {
//        //given
//        String accessToken = jwtCreateService.createAccessToken(UNIQUE_ID);
//
//        //when
//        Claims claims = jwtParseService.parseClaims(accessToken);
//
//        //then
//        assertThat(claims.getSubject()).isEqualTo(UNIQUE_ID);
//    }
//
//    @Test
//    @DisplayName("parseClaims() : 토큰의 만료 기간이 지날 경우 ExpiredJwtTokenException 을 던진다")
//    void test_parseClaims_throw_ExpiredJwtTokenException() throws Exception {
//        //when
//        String testToken = createTestToken(secret, new Date(1));
//
//        //then
//        assertThrows(ExpiredJwtTokenException.class,
//                     () -> {
//                         jwtParseService.parseClaims(testToken);
//                     });
//    }
//
//
//}
