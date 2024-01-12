package com.example.seob.user.service;

import com.example.seob.user.domain.entity.Member;
import com.example.seob.user.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/*
 * 인증에 필요한 UserDetailsService interface의 loadUserByUsername 메서드를 구현하는 클래스로
 * loadUserByUsername 메서드를 통해 Database에 접근하여 사용자 정보를 가지고 옴
 *  */

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return memberRepository.findByEmail(username)
                .map(this::createUserDetails)
                .orElseThrow(() -> new UsernameNotFoundException("해당하는 유저를 찾을 수 없습니다"));

    }

    // 해당하는 User의 데이터가 존재한다면 UserDetails 객체로 만들어서 리턴
    private UserDetails createUserDetails(Member member) {

        return new User(member.getUsername(), member.getPassword(), member.getAuthorities());
    }
}
