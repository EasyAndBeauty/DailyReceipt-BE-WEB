package com.sprint.dailyreceipt.infra.kakao.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class KakaoProfileResponse {

    String id;

    public KakaoProfileResponse(String id) {
        this.id = id;
    }
}
