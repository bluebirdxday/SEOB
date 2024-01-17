package com.example.seob.user.controller;

import com.example.seob.global.response.ApiDataResponse;
import com.example.seob.user.domain.dto.CreateMemberDto;
import com.example.seob.user.domain.dto.MemberRequestDto;
import com.example.seob.user.domain.dto.MemberResponseDto;
import com.example.seob.user.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/member")
public class MemberController {

    private final MemberService memberService;

    // 회원가입
    @PostMapping("/sign-up")
    public ApiDataResponse<CreateMemberDto.Response> signUp(
            @RequestBody @Valid CreateMemberDto.Request userCreateRequestDto
    ) {
        return ApiDataResponse.of(
                CreateMemberDto.Response.fromDto(
                        memberService.signUp(userCreateRequestDto.toDto()
                        )
                )
        );
    }

    // 로그인
    @PostMapping(value = "/login")
    public ApiDataResponse<MemberResponseDto.TokenInfo> login(
            @RequestBody @Valid MemberRequestDto.Login loginRequestDto
    ) {
        return ApiDataResponse.of(memberService.login(loginRequestDto.toDto()));
    }


    /*
     * 로그아웃
     * TODO : 테스트 필요
     * */
    @RequestMapping(value = "/logout", method = {RequestMethod.GET, RequestMethod.POST})
    public ApiDataResponse<?> logout(
            @RequestHeader("Authorization") String accessToken
    ) {
        return ApiDataResponse.of(
                CreateMemberDto.Response.fromDto(
                        memberService.logout(accessToken)
                ));
    }
}
