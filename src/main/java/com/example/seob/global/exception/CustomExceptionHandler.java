package com.example.seob.global.exception;

import com.example.seob.global.response.ApiErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestControllerAdvice(annotations = {RestController.class})
public class CustomExceptionHandler {

    @ExceptionHandler(CustomException.class)
    protected ApiErrorResponse handleCustomException(
            CustomException e,
            HttpServletRequest request) {

        log.error("errorCode: {}, url: {}, message: {}",
                e.getErrorCode(), request.getRequestURI(), e.getMessage());

        return ApiErrorResponse.of(false, e.getErrorCode());
    }
}
