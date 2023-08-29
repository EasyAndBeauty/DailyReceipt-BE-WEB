//package com.sprint.dailyreceipt.infra.kakao;
//
//import com.sprint.dailyreceipt.domain.account.application.AccountSignInService;
//import com.sprint.dailyreceipt.infra.kakao.model.KakaoProfileResponse;
//import com.sprint.dailyreceipt.infra.kakao.model.KakaoTokenResponse;
//import com.sprint.dailyreceipt.domain.token.api.model.TokenResponse;
//import lombok.RequiredArgsConstructor;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//
//import static com.sprint.dailyreceipt.global.ReceiptConstants.AUTHORIZATION_HEADER_BEARER;
//import static com.sprint.dailyreceipt.global.ReceiptConstants.GRANT_TYPE;
//import static com.sprint.dailyreceipt.global.ReceiptConstants.KAKAO_CONTENT_TYPE;
//import static com.sprint.dailyreceipt.global.ReceiptConstants.LOCAL_REDIRECT_URI;
//
//@RestController
//@RequiredArgsConstructor
//public class KakaoApi {
//
//    private final KakaoTokenClient kakaoTokenClient;
//
//    private final KakaoAccountInfoClient kakaoAccountInfoClient;
//
//    private final AccountSignInService accountSignInService;
//
//    @Value("${social.kakao.client-id}")
//    private String clientId;
//
//    @Value("${social.kakao.client-secret}")
//    private String clientSecret;
//
//    @Value("${private.ip}")
//    private String redirectUri;
//
//    @GetMapping("/auth/kakao/callback")
//    public TokenResponse callbackOfKakao(@RequestParam String code){
//        KakaoTokenResponse kakaoTokenResponse = kakaoTokenClient.requestKakaoToken(KAKAO_CONTENT_TYPE, GRANT_TYPE,
//                                                                                   clientId, redirectUri,
//                                                                                   code, clientSecret);
//
//        KakaoProfileResponse kakaoProfileResponse = kakaoAccountInfoClient.getAccessTokenForUserInfo(
//                KAKAO_CONTENT_TYPE, AUTHORIZATION_HEADER_BEARER + kakaoTokenResponse.getAccessToken());
//
//        return accountSignInService.signIn(kakaoProfileResponse);
//    }
//}
