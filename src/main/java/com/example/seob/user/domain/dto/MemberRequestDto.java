package com.example.seob.user.domain.dto;

import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class MemberRequestDto {


    @Getter
    public static class Login {

        @NotBlank
        @Pattern(regexp = "^[A-Za-z0-9._%+-]{1,63}@[A-Za-z0-9.-]{1,190}\\.[A-Za-z]{2,6}$")
        private String email;

        @NotBlank
        @Size(min = 10, max = 100)
        private String password;

        public MemberDto toDto() {
            return MemberDto.of(null, email, password, null, null);
        }
    }

    @Getter
    public static class Logout {

        @NotEmpty(message = "잘못된 요청입니다.")
        private String accessToken;

        @NotEmpty(message = "잘못된 요청입니다.")
        private String refreshToken;
    }

    @Getter
    public static class Reissue {

        @NotBlank(message = "accessToken을 입력해주세요.")
        private String accessToken;

        @NotBlank(message = "refreshToken을 입력해주세요.")
        private String refreshToken;
    }
}
