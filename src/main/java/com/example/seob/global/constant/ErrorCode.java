package com.example.seob.global.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

import java.util.Optional;
import java.util.function.Predicate;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    // 4XX ERROR
    /* 400 BAD_REQUEST : 잘못된 요청 */
    BAD_REQUEST(10000, HttpStatus.BAD_REQUEST, "잘못된 요청입니다."),
    TOKEN_TYPE(10001, HttpStatus.BAD_REQUEST, "토큰 타입이 올바르지 않습니다."),
    UNAVAILABLE_REFRESH_TOKEN(10002, HttpStatus.BAD_REQUEST, "사용할 수 없는 토큰 입니다."),
    MISMATCH_ID_OR_PASSWORD(10003, HttpStatus.BAD_REQUEST, "아이디 혹은 비밀번호가 일치하지 않습니다."),

    /* 404 NOT_FOUND : Resource 를 찾을 수 없음 */
    EMAIL_NOT_FOUND(20000, HttpStatus.NOT_FOUND, "해당 이메일을 찾을 수 없습니다."),
    REFRESH_TOKEN_NOT_FOUND(20001, HttpStatus.NOT_FOUND, "Refresh Token을 찾을 수 없습니다."),
    MISMATCH_REFRESH_TOKEN(20002, HttpStatus.NOT_FOUND, "Refresh Token 정보가 일치하지 않습니다."),

    /* 409 CONFLICT : Resource 의 현재 상태와 충돌. 보통 중복된 데이터 존재 */
    DUPLICATE_EMAIL(30000, HttpStatus.CONFLICT, "이메일이 이미 존재합니다."),
    DELETED_EMAIL(30001, HttpStatus.CONFLICT, "이미 삭제된 이메일 입니다."),

    // 5XX ERROR
    INTERNAL_ERROR(40000, HttpStatus.INTERNAL_SERVER_ERROR, "Internal error"),
    SPRING_INTERNAL_ERROR(40001, HttpStatus.INTERNAL_SERVER_ERROR, "Spring-detected internal error"),
    DATA_ACCESS_ERROR(40002, HttpStatus.INTERNAL_SERVER_ERROR, "Data access error");

    private final Integer code;
    private final HttpStatus httpStatus;
    private final String message;

    public String getMessage(Throwable e) {
        return this.getMessage(this.getMessage() + " - " + e.getMessage());
    }

    public String getMessage(String message) {
        return Optional.ofNullable(message)
                .filter(Predicate.not(String::isBlank))
                .orElse(this.getMessage());
    }

    @Override
    public String toString() {
        return String.format("%s (%d)", this.name(), this.getCode());
    }
}
