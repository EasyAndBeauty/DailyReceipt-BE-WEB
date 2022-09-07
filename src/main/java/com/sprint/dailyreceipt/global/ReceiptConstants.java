package com.sprint.dailyreceipt.global;

public class ReceiptConstants {

    //Kakao OAuth

    public static final String LOCAL_REDIRECT_URI = "http://localhost:8080/auth/kakao/callback";

    public static final String KAKAO_CONTENT_TYPE = "application/x-www-form-urlencoded;charset=utf-8";

    public static final String GRANT_TYPE = "authorization_code";

    //JWT

    public static final String AUTHORIZATION_HEADER = "Authorization";

    public static final String AUTHORIZATION_HEADER_BEARER = "Bearer ";

    public static final String AUTHORITIES_KEY = "auth";

    public static final String JWT_HEADER_PARAM_TYPE = "typ";

    public static final String ROLE = "USER";

    //Exception

    public static final String ERROR_LOG_MESSAGE = "Exception = {} , message = {}";

    public static final String CONTENT_TYPE = "application/json";

    public static final String CHARACTER_ENCODING = "UTF-8";

}
