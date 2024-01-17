package com.example.seob.global.exception;

import com.example.seob.global.constant.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CustomException extends RuntimeException {
    private final ErrorCode errorCode;
}
