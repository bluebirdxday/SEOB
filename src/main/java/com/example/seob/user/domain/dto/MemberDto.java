package com.example.seob.user.domain.dto;

import com.example.seob.user.domain.entity.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class MemberDto {

    private Long memberId;
    private String email;
    private String password;
    private String name;
    private List<String> roles;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private Long creatorId;
    private Long modifierId;

    @Builder
    public MemberDto(
            Long memberId,
            String email,
            String password,
            String name,
            List<String> roles,
            LocalDateTime createdAt,
            LocalDateTime modifiedAt,
            Long creatorId,
            Long modifierId
    ) {
        this.memberId = memberId;
        this.email = email;
        this.password = password;
        this.name = name;
        this.roles = roles;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
        this.creatorId = creatorId;
        this.modifierId = modifierId;
    }

    public static MemberDto of(
            Long memberId,
            String email,
            String password,
            String name,
            List<String> roles
    ) {
        return MemberDto.builder()
                .memberId(memberId)
                .email(email)
                .password(password)
                .name(name)
                .roles(roles)
                .build();
    }

    public static MemberDto of(Member member) {
        return MemberDto.builder()
                .memberId(member.getMemberId())
                .email(member.getEmail())
                .password(member.getPassword())
                .name(member.getName())
                .roles(member.getRoles())
                .createdAt(member.getCreatedAt())
                .modifiedAt(member.getModifiedAt())
                .creatorId(member.getCreatorId())
                .modifierId(member.getModifierId())
                .build();
    }

    public Member toEntity() {
        return Member.of(email, password, name, roles);
    }

    public void encryptPassword(String bcryptPassword) {
        this.password = bcryptPassword;
    }

    public UsernamePasswordAuthenticationToken toAuthentication() {
        return new UsernamePasswordAuthenticationToken(email, password);
    }

}
