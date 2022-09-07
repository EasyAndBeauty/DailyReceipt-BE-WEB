package com.sprint.dailyreceipt.global.utils;

import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;

import static com.sprint.dailyreceipt.global.ReceiptConstants.*;

public class JwtUtil {

    public static String resolveToken(HttpServletRequest request) {
        String headerToken = request.getHeader(AUTHORIZATION_HEADER);

        if (StringUtils.hasText(headerToken) && headerToken.startsWith(AUTHORIZATION_HEADER_BEARER)) {
            return headerToken.substring(AUTHORIZATION_HEADER_BEARER.length());
        }

        return null;
    }
}
