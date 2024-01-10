package com.example.seob.user.domain.dto;

import com.example.seob.user.domain.entity.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class UserDto {

    private Long userId;
    private String email;
    private String password;
    private String name;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private Long creatorId;
    private Long modifierId;

    @Builder
    public UserDto(
            Long userId,
            String email,
            String password,
            String name,
            LocalDateTime createdAt,
            LocalDateTime modifiedAt,
            Long creatorId,
            Long modifierId
    ) {
        this.userId = userId;
        this.email = email;
        this.password = password;
        this.name = name;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
        this.creatorId = creatorId;
        this.modifierId = modifierId;
    }

    public static UserDto of(
            Long userId,
            String email,
            String password,
            String name,
            LocalDateTime createdAt,
            LocalDateTime modifiedAt,
            Long creatorId,
            Long modifierId
    ) {
        return UserDto.builder()
                .userId(userId)
                .email(email)
                .password(password)
                .name(name)
                .createdAt(createdAt)
                .creatorId(creatorId)
                .modifiedAt(modifiedAt)
                .modifierId(modifierId)
                .build();
    }

    public static UserDto of(User user) {
        return UserDto.builder()
                .userId(user.getUserId())
                .email(user.getEmail())
                .password(user.getPassword())
                .name(user.getName())
                .createdAt(user.getCreatedAt())
                .modifiedAt(user.getModifiedAt())
                .creatorId(user.getCreatorId())
                .modifierId(user.getModifierId())
                .build();
    }

    public User toEntity() {
        return User.of(email, password, name);
    }

    public void encryptPassword(String bcryptPassword) {
        this.password = bcryptPassword;
    }
}
