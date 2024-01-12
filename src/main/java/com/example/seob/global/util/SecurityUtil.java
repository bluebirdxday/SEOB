package com.example.seob.global.util;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/*
 * 클라이언트 요청 시 JwtAuthenticationFilter에서 인증되어
 * SecurityContextHolder에 저장된 Authentication 객체 정보를 가져오기 위해서 만든 클래스
 * */
public class SecurityUtil {

    public static String getCurrentUserEmail() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || authentication.getName() == null) {
            throw new RuntimeException("No authentication information");
        }

        return authentication.getName();
    }
}
