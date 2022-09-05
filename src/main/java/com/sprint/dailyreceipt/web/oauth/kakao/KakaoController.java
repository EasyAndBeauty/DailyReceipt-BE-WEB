package com.sprint.dailyreceipt.web.oauth.kakao;

import com.sprint.dailyreceipt.domain.account.service.AccountService;
import com.sprint.dailyreceipt.web.oauth.kakao.model.KakaoTokenResponse;
import com.sprint.dailyreceipt.web.oauth.kakao.model.KakaoProfileResponse;
import com.sprint.dailyreceipt.web.token.model.TokenResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static com.sprint.dailyreceipt.global.ReceiptConstants.AUTHORIZATION_HEADER_BEARER;
import static com.sprint.dailyreceipt.global.ReceiptConstants.GRANT_TYPE;
import static com.sprint.dailyreceipt.global.ReceiptConstants.KAKAO_CONTENT_TYPE;
import static com.sprint.dailyreceipt.global.ReceiptConstants.LOCAL_REDIRECT_URI;

@RestController
@RequiredArgsConstructor
public class KakaoController {

    private final KakaoClientForToken kakaoClientForToken;

    private final KakaoClientForAccountInfo kakaoClientForAccountInfo;

    private final AccountService accountService;

    @Value("${social.kakao.client-id}")
    private String clientId;

    @Value("${social.kakao.client-secret}")
    private String clientSecret;

    @Value("${private.ip}")
    private String redirectUri;

    @GetMapping("/auth/kakao/callback")
    public TokenResponse callbackOfKakao(@RequestParam String code){
        KakaoTokenResponse kakaoTokenResponse = kakaoClientForToken.requestKakaoToken(KAKAO_CONTENT_TYPE, GRANT_TYPE,
                                                                                      clientId, redirectUri,
                                                                                      code, clientSecret);

        KakaoProfileResponse kakaoProfileResponse = kakaoClientForAccountInfo.getAccessTokenForUserInfo(
                KAKAO_CONTENT_TYPE, AUTHORIZATION_HEADER_BEARER + kakaoTokenResponse.getAccessToken());

        return accountService.signIn(kakaoProfileResponse);
    }
}
