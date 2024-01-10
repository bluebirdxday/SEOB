package com.example.seob.user.controller;

import com.example.seob.global.response.ApiDataResponse;
import com.example.seob.user.domain.dto.CreateUserDto;
import com.example.seob.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class SignController {

    private final UserService userService;


    @PostMapping("/sign-up")
    public ApiDataResponse<CreateUserDto.Response> createUser(
            @RequestBody @Valid CreateUserDto.Request userCreateRequestDto
    ) {
        return ApiDataResponse.of(
                CreateUserDto.Response.fromDto(
                        userService.createUser(userCreateRequestDto.toDto()
                        )
                )
        );
    }
}
