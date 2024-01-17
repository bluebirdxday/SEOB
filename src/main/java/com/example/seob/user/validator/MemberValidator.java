package com.example.seob.user.validator;

import com.example.seob.global.constant.ErrorCode;
import com.example.seob.global.exception.CustomException;
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
            throw new CustomException(ErrorCode.DUPLICATE_EMAIL);
    }

    public void findByEmailAndPassword(MemberDto memberDto) {
        memberRepository.findByEmail(memberDto.getEmail())
                .filter(foundMember -> encoder.matches(memberDto.getPassword(), foundMember.getPassword()))
                .orElseThrow(() -> new CustomException(ErrorCode.MISMATCH_ID_OR_PASSWORD));
    }
}
