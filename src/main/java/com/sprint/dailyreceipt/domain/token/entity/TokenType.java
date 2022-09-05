package com.sprint.dailyreceipt.domain.token.entity;

public enum TokenType {
    ACCESS, REFRESH;

    public static boolean isRefreshToken(String tokenType) {
        return TokenType.REFRESH.name()
                                .equals(tokenType);
    }

}
