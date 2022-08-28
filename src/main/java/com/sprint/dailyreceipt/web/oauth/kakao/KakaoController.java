package com.sprint.dailyreceipt.web.oauth.kakao;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.sprint.dailyreceipt.domain.service.AccountService;
import com.sprint.dailyreceipt.web.oauth.kakao.model.KakaoTokenResponse;
import com.sprint.dailyreceipt.web.oauth.kakao.model.KakaoUserInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static com.sprint.dailyreceipt.global.ReceiptConstants.GRANT_TYPE;
import static com.sprint.dailyreceipt.global.ReceiptConstants.KAKAO_CONTENT_TYPE;

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
    private String ipUrl;

    @GetMapping("/auth/kakao/callback")
    public ResponseEntity<KakaoUserInfo> callbackOfKakao(@RequestParam String code){
        StringBuilder url = new StringBuilder(ipUrl);

        String callBackUrl = url.append(":8080")
                                .append("/auth")
                                .append("/kakao")
                                .append("callback")
                                .toString();

        System.out.println("callBackUrl = " + callBackUrl);

        KakaoTokenResponse kakaoTokenResponse = kakaoClientForToken.requestKakaoToken(KAKAO_CONTENT_TYPE, GRANT_TYPE,
                                                                                      clientId, callBackUrl,
                                                                                      code, clientSecret);

        String result = kakaoClientForAccountInfo.accessToken(KAKAO_CONTENT_TYPE, "Bearer " + kakaoTokenResponse.getAccessToken());

        JsonParser parser = new JsonParser();
        JsonElement element = parser.parse(result);

        String id = element.getAsJsonObject().get("id").getAsString();
        String email = element.getAsJsonObject().get("kakao_account").getAsJsonObject().get("email").getAsString();

        KakaoUserInfo kakaoUserInfo = new KakaoUserInfo(id, email);

        accountService.signIn(kakaoUserInfo);

        return ResponseEntity.ok(kakaoUserInfo);
    }

}