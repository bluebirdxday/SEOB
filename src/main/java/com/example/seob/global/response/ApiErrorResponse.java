package com.example.seob.global.response;

import com.example.seob.constant.ErrorCode;
import lombok.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

@Getter
@ToString
@EqualsAndHashCode
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class ApiErrorResponse {

    private final boolean success;
    private final Integer errorCode;
    private final String message;
    private final HashMap<String, List<String>> details;
    private final LocalDateTime timestamp = LocalDateTime.now();

    public static ApiErrorResponse of(Boolean success, ErrorCode errorCode) {
        return new ApiErrorResponse(success, errorCode.getCode(), errorCode.getMessage(), null);
    }

    public static ApiErrorResponse of(Boolean success, ErrorCode errorCode, Exception e) {
        return new ApiErrorResponse(success, errorCode.getCode(), errorCode.getMessage(e), null);
    }

    public static ApiErrorResponse of(Boolean success, ErrorCode errorCode, String message) {
        return new ApiErrorResponse(success, errorCode.getCode(), errorCode.getMessage(message), null);
    }

    public static ApiErrorResponse of(Boolean success, ErrorCode errorCode, HashMap<String, List<String>> details) {
        return new ApiErrorResponse(success, errorCode.getCode(), errorCode.getMessage(), details);
    }

    public static ApiErrorResponse of(Boolean success, ErrorCode errorCode, String message, HashMap<String, List<String>> details) {
        return new ApiErrorResponse(success, errorCode.getCode(), errorCode.getMessage(message), details);
    }
}
