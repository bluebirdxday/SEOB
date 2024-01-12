package com.example.seob.user.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

public class CreateMemberDto {

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

        public MemberDto toDto() {
            return MemberDto.of(null, email, password, name, null);
        }
    }


    @Getter
    @AllArgsConstructor
    public static class Response {

        private Long memberId;
        private String email;
        private String name;
        private LocalDateTime createdAt;

        public static Response of(
                Long memberId,
                String email,
                String name,
                LocalDateTime createdAt
        ) {
            return new Response(memberId, email, name, createdAt);
        }

        public static Response fromDto(MemberDto userDto) {
            if (userDto == null) return null;

            return Response.of(
                    userDto.getMemberId(),
                    userDto.getEmail(),
                    userDto.getName(),
                    userDto.getCreatedAt()
            );
        }
    }

}
