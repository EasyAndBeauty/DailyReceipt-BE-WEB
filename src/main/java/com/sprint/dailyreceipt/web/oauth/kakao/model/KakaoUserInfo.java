package com.sprint.dailyreceipt.web.oauth.kakao.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class KakaoUserInfo {

    String id;

    String email;

    public KakaoUserInfo(String id, String email) {
        this.id = id;
        this.email = email;
    }
}
