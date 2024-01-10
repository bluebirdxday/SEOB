package com.example.seob.user.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

public class CreateUserDto {

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Request {

        @NotBlank
        @Pattern(regexp = "^[A-Za-z0-9._%+-]{1,63}@[A-Za-z0-9.-]{1,190}\\.[A-Za-z]{2,6}$")
        private String email;

        @NotBlank
        @Size(min = 10, max = 100)
        private String password;

        @NotBlank
        @Size(min = 2, max = 30)
        private String name;

        public UserDto toDto() {
            return UserDto.of(null, email, password, name, LocalDateTime.now(), null, null, null);
        }
    }


    @Getter
    @AllArgsConstructor
    public static class Response {

        private Long userId;
        private String email;
        private String name;
        private LocalDateTime createdAt;

        public static Response of(
                Long userId,
                String email,
                String name,
                LocalDateTime createdAt
        ) {
            return new Response(userId, email, name, createdAt);
        }

        public static Response fromDto(UserDto userDto) {
            if (userDto == null) return null;

            return Response.of(
                    userDto.getUserId(),
                    userDto.getEmail(),
                    userDto.getName(),
                    userDto.getCreatedAt()
            );
        }
    }

}
