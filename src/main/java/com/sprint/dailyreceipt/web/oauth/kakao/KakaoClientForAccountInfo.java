package com.sprint.dailyreceipt.web.oauth.kakao;

import com.sprint.dailyreceipt.web.oauth.kakao.model.KakaoProfileResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "KakaoClientForAccountInfo", url = "https://kapi.kakao.com")
public interface KakaoClientForAccountInfo {

    @GetMapping(value = "/v2/user/me", consumes = "application/json")
    KakaoProfileResponse getAccessTokenForUserInfo(
            @RequestHeader("Content-Type") String contentType,
            @RequestHeader("Authorization") String token
    );
}
