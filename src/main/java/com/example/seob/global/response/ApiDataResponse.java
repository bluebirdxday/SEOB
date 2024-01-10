package com.example.seob.global.response;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ApiDataResponse<T> {

    private final boolean success;
    private final HttpStatus status;
    private final T data;

    public ApiDataResponse(T data) {
        this.success = true;
        this.status = HttpStatus.OK;
        this.data = data;
    }

    public ApiDataResponse(T data, HttpStatus status) {
        this.success = true;
        this.status = status;
        this.data = data;
    }

    public static <T> ApiDataResponse<T> of(T data) {
        return new ApiDataResponse<>(data);
    }
    
    public static <T> ApiDataResponse<T> ok() {
        return new ApiDataResponse<>(null, HttpStatus.OK);
    }

    public static <T> ApiDataResponse<T> noContent() {
        return new ApiDataResponse<>(null, HttpStatus.NO_CONTENT);
    }

}
