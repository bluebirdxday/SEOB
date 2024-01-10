package com.example.seob.user.service;

import com.example.seob.global.exception.ApiRequestException;
import com.example.seob.user.domain.dto.UserDto;
import com.example.seob.user.domain.entity.User;
import com.example.seob.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder encoder;

    @Transactional
    public UserDto createUser(UserDto userDto) {
        // 비밀번호 암호화
        userDto.encryptPassword(encoder.encode(userDto.getPassword()));

        // 이메일 중복 검사
        validateUserEmailExists(userDto);

        User user = userRepository.save(userDto.toEntity());

        System.out.println(user);

        // 회원 영속화
        return UserDto.of(user);
    }

    private void validateUserEmailExists(UserDto createUserDto) {
        userRepository.findByEmail(createUserDto.getEmail())
                .ifPresent(user -> {
                    throw new ApiRequestException("이미 사용중인 이메일입니다.");
                });
    }

}
