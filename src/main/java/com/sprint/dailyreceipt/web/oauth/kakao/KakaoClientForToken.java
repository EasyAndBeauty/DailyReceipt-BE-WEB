package com.sprint.dailyreceipt.web.oauth.kakao;

import com.sprint.dailyreceipt.web.oauth.kakao.model.KakaoTokenResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "KakaoClientForToken", url = "https://kauth.kakao.com")
public interface KakaoClientForToken {

    @PostMapping(value = "/oauth/token", consumes = "application/json")
    KakaoTokenResponse requestKakaoToken(
            @RequestHeader("Content-Type") String contentType,
            @RequestParam("grant_type") String grantType,
            @RequestParam("client_id") String clientId,
            @RequestParam("redirect_uri") String redirectUri,
            @RequestParam("code") String code,
            @RequestParam("client_secret") String clientSecret
    );


}
