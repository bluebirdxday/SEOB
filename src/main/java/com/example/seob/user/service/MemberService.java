package com.example.seob.user.service;

import com.example.seob.global.enumeration.Authority;
import com.example.seob.global.exception.ApiRequestException;
import com.example.seob.jwt.JwtProperties;
import com.example.seob.jwt.JwtTokenProvider;
import com.example.seob.user.domain.dto.MemberDto;
import com.example.seob.user.domain.dto.MemberRequestDto;
import com.example.seob.user.domain.dto.MemberResponseDto;
import com.example.seob.user.repository.MemberRepository;
import com.example.seob.user.validator.MemberValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.Collections;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    private final PasswordEncoder encoder;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final JwtTokenProvider jwtTokenProvider;
    private final RedisTemplate redisTemplate;
    private final MemberValidator memberValidator;

    @Transactional
    public MemberDto signUp(MemberDto memberDto) {
        // 비밀번호 암호화
        memberDto.encryptPassword(encoder.encode(memberDto.getPassword()));

        // 이메일 중복 검사
        memberValidator.validateUserEmailExists(memberDto);

        // ROLE을 회원으로 지정
        memberDto.setRoles(Collections.singletonList(Authority.ROLE_USER.name()));

        // 회원 영속화
        return MemberDto.of(memberRepository.save(memberDto.toEntity()));
    }

    public MemberResponseDto.TokenInfo login(MemberDto memberDto) {

        // 회원 존재 유무 검사
        memberValidator.findByEmailAndPassword(memberDto);

        // 1. Login ID/PW를 기반으로 Authentication 객체 생성
        // 이때 authentication는 인증 여부를 확인하는 authenticated 값이 false
        UsernamePasswordAuthenticationToken authenticationToken = memberDto.toAuthentication();

        // 2. 실제 검증 (사용자 비밀번호 체크)이 이루어지는 부분
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        // 3. 인증 정보를 기반으로 JWT 토큰 생성
        MemberResponseDto.TokenInfo tokenInfo = jwtTokenProvider.generateToken(authentication);

        // 4. RefreshToken Redis 저장 (expirationTime 설정을 통해 자동 삭제 처리)
        redisTemplate.opsForValue()
                .set(JwtProperties.REFRESH_TOKEN_KEY + authentication.getName(), tokenInfo.getRefreshToken(), tokenInfo.getRefreshTokenExpirationTime(), TimeUnit.MILLISECONDS);

        return tokenInfo;
    }


    public MemberResponseDto.TokenInfo reissue(MemberRequestDto.Reissue reissueRequest) {
        // 1. Refresh Token 검증
        if (!jwtTokenProvider.validateToken(reissueRequest.getRefreshToken()))
            throw new ApiRequestException("잘못된 요청입니다.");

        // 2. Access Token에서 User email을 가져옴
        Authentication authentication = jwtTokenProvider.getAuthentication(reissueRequest.getAccessToken());

        // 3. Redis에서 Uer email을 기반으로 저장된 Refresh Token 값을 가져옴
        String refreshToken = (String) redisTemplate.opsForValue().get("RT:" + authentication.getName());
        // 로그아웃 되어 Redis에 RefreshToken이 존재하지 않는 경우 처리
        if (ObjectUtils.isEmpty(refreshToken))
            throw new ApiRequestException("잘못된 요청입니다.");

        if (!refreshToken.equals(reissueRequest.getRefreshToken()))
            throw new ApiRequestException("Refresh Token 정보가 일치하지 않습니다.");

        // 4. 새로운 토큰 생성
        MemberResponseDto.TokenInfo tokenInfo = jwtTokenProvider.generateToken(authentication);

        // 5. RefreshToken Redis 업데이트
        redisTemplate.opsForValue()
                .set(JwtProperties.REFRESH_TOKEN_KEY + authentication.getName(), tokenInfo.getRefreshToken(), tokenInfo.getRefreshTokenExpirationTime(), TimeUnit.MILLISECONDS);

        return tokenInfo;
    }


    public void logout(MemberRequestDto.Logout logoutRequest) {
        // 1. Access Token 검증
        if (!jwtTokenProvider.validateToken(logoutRequest.getAccessToken()))
            throw new ApiRequestException("잘못된 요청입니다.");

        // 2. Access Token에서 User email을 가져옴
        Authentication authentication = jwtTokenProvider.getAuthentication(logoutRequest.getAccessToken());

        // 3. Redis에서 해당 User email로 저장된 Refresh Token이 있는지 여부를 확인 후 있을 경우 삭제함
        if (redisTemplate.opsForValue().get(JwtProperties.REFRESH_TOKEN_KEY + authentication.getName()) != null)
            // Refresh Token 삭제
            redisTemplate.delete(JwtProperties.REFRESH_TOKEN_KEY + authentication.getName());

        // 4. 해당 Access Token 유효시간 가지고 와서 BlackList로 저장하기
        Long expiration = jwtTokenProvider.getExpiration(logoutRequest.getAccessToken());
        redisTemplate.opsForValue()
                .set(logoutRequest.getAccessToken(), "logout", expiration, TimeUnit.MILLISECONDS);
    }

}
