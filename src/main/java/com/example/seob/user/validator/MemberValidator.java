package com.example.seob.user.validator;

import com.example.seob.global.exception.ApiRequestException;
import com.example.seob.user.domain.dto.MemberDto;
import com.example.seob.user.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberValidator {

    private final MemberRepository memberRepository;
    private final PasswordEncoder encoder;

    public void validateUserEmailExists(MemberDto memberDto) {
        if (memberRepository.existsByEmail(memberDto.getEmail()))
            throw new ApiRequestException("이미 사용중인 이메일입니다.");
    }

    public void findByEmailAndPassword(MemberDto memberDto) {
        memberRepository.findByEmail(memberDto.getEmail())
                .filter(foundMember -> encoder.matches(memberDto.getPassword(), foundMember.getPassword()))
                .orElseThrow(() -> new ApiRequestException("이메일 혹은 비밀번호가 일치하지 않습니다."));
    }
}
