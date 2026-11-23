package com.cotato.recruit.global.common.response;

import com.cotato.recruit.global.exception.ErrorCode;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import lombok.Getter;

/**
 * 실패 응답을 감싸는 공통 래퍼. 디버깅을 위해 code, message 와 함께 요청의 method, path 를 담는다.
 * 스택트레이스 같은 내부 정보는 담지 않는다. 성공 응답은 {@link ApiResponse} 가 맡는다.
 */
@Getter
public class ErrorResponse {

    private final boolean success = false;
    private final int status;
    private final String code;
    private final String message;
    private final String method;
    private final String path;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private final LocalDateTime timestamp = LocalDateTime.now();

    private ErrorResponse(int status, String code, String message, String method, String path) {
        this.status = status;
        this.code = code;
        this.message = message;
        this.method = method;
        this.path = path;
    }

    public static ErrorResponse of(ErrorCode errorCode, HttpServletRequest request) {
        return new ErrorResponse(
                errorCode.getHttpStatus().value(),
                errorCode.getCode(),
                errorCode.getMessage(),
                request.getMethod(),
                request.getRequestURI()
        );
    }

    public static ErrorResponse of(ErrorCode errorCode, String customMessage, HttpServletRequest request) {
        return new ErrorResponse(
                errorCode.getHttpStatus().value(),
                errorCode.getCode(),
                customMessage,
                request.getMethod(),
                request.getRequestURI()
        );
    }
}
