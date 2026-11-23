package com.cotato.recruit.global.exception;

import com.cotato.recruit.global.common.response.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

/**
 * 컨트롤러에서 발생한 예외를 한곳에서 {@link ErrorResponse} 로 바꾸는 전역 핸들러.
 * 공통 예외, 입력 오류(400), 없는 경로(404), 지원하지 않는 메서드(405),
 * 그 밖의 예상 못 한 예외(500)를 처리한다.
 * 인증·권한 실패(401/403)는 Security 필터에서 발생하므로 여기서 다루지 않는다.
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidation(
            MethodArgumentNotValidException e, HttpServletRequest request
    ) {
        String detail = e.getBindingResult().getFieldErrors().stream()
                .findFirst()
                .map(FieldError::getDefaultMessage)
                .orElse(ErrorCode.COMMON_BAD_REQUEST.getMessage());

        log.warn("[Validation] {} {} | {}",
                request.getMethod(), request.getRequestURI(), detail);

        return ResponseEntity
                .status(ErrorCode.COMMON_BAD_REQUEST.getHttpStatus())
                .body(ErrorResponse.of(ErrorCode.COMMON_BAD_REQUEST, detail, request));
    }

    @ExceptionHandler({
            HttpMessageNotReadableException.class,
            MethodArgumentTypeMismatchException.class,
            MissingServletRequestParameterException.class
    })
    public ResponseEntity<ErrorResponse> handleBadRequest(
            Exception e, HttpServletRequest request
    ) {
        log.warn("[BadRequest] {} {} | {}",
                request.getMethod(), request.getRequestURI(), e.getClass().getSimpleName());

        return ResponseEntity
                .status(ErrorCode.COMMON_BAD_REQUEST.getHttpStatus())
                .body(ErrorResponse.of(ErrorCode.COMMON_BAD_REQUEST, request));
    }

    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFound(
            NoResourceFoundException e, HttpServletRequest request
    ) {
        return ResponseEntity
                .status(ErrorCode.COMMON_NOT_FOUND.getHttpStatus())
                .body(ErrorResponse.of(ErrorCode.COMMON_NOT_FOUND, request));
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ErrorResponse> handleMethodNotAllowed(
            HttpRequestMethodNotSupportedException e, HttpServletRequest request
    ) {
        log.warn("[MethodNotAllowed] {} {}", request.getMethod(), request.getRequestURI());

        return ResponseEntity
                .status(ErrorCode.COMMON_METHOD_NOT_ALLOWED.getHttpStatus())
                .body(ErrorResponse.of(ErrorCode.COMMON_METHOD_NOT_ALLOWED, request));
    }

    @ExceptionHandler(RecruitException.class)
    public ResponseEntity<ErrorResponse> handleRecruitException(
            RecruitException e, HttpServletRequest request
    ) {
        log.warn("[RecruitException] {} {} | code={} | message={}",
                request.getMethod(), request.getRequestURI(),
                e.getErrorCode().getCode(), e.getMessage());

        return ResponseEntity
                .status(e.getErrorCode().getHttpStatus())
                .body(ErrorResponse.of(e.getErrorCode(), e.getMessage(), request));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(
            Exception e, HttpServletRequest request
    ) {
        log.error("[UnhandledException] {} {} | {}",
                request.getMethod(), request.getRequestURI(), e.getMessage(), e);

        return ResponseEntity
                .status(ErrorCode.COMMON_INTERNAL_SERVER_ERROR.getHttpStatus())
                .body(ErrorResponse.of(ErrorCode.COMMON_INTERNAL_SERVER_ERROR, request));
    }
}
