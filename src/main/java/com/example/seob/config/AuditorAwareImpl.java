package com.example.seob.config;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

/*
 * 생성자와 수정자 정보를 자동으로 업데이트 시키기 위해 필요한 config class
 */
public class AuditorAwareImpl implements AuditorAware<Long> {

    Long userId = 1L;

    @Override
    public Optional<Long> getCurrentAuditor() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (null == authentication || !authentication.isAuthenticated())
            return null;

        // 추후에 request에서 id를 받아와서 반환해주기
        return Optional.ofNullable(userId);
    }
}
