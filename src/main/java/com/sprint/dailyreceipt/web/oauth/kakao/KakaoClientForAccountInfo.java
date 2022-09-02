package com.sprint.dailyreceipt.web.oauth.kakao;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "KakaoClientForAccountInfo", url = "https://kapi.kakao.com")
public interface KakaoClientForAccountInfo {

    @GetMapping(value = "/v2/user/me", consumes = "application/json")
    String accessToken(
            @RequestHeader("Content-Type") String contentType,
            @RequestHeader("Authorization") String token
    );
}
