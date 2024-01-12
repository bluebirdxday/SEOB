package com.example.seob.jwt;

/**
 * JWT 기본 설정값
 */
public class JwtProperties {
    public static final String AUTHORITIES_KEY = "auth";
    public static final String BEARER_TYPE = "Bearer";
    public static final long ACCESS_TOKEN_EXPIRE_TIME = 30 * 60 * 1000L;   // 30분
    public static final long REFRESH_TOKEN_EXPIRE_TIME = 7 * 24 * 60 * 60 * 1000L;    // 7일
    public static final String REFRESH_TOKEN_KEY = "RT:";
    public static final String AUTHORIZATION_HEADER = "Authorization";
}
