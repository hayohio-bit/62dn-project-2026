package com.dnproject.platform.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class ErrorResponse {
    private final LocalDateTime timestamp = LocalDateTime.now();
    private final int status;
    private final String error;
    private final String code;
    private final String message;

    // 기본적인 생성 메서드
    public static ErrorResponse of(int status, String error, String code, String message) {
        return ErrorResponse.builder()
                .status(status)
                .error(error)
                .code(code)
                .message(message)
                .build();
    }
}
