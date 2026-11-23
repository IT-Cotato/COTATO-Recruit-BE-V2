package com.cotato.recruit.global.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

/**
 * 모든 에러의 HTTP 상태, 코드, 메시지를 한곳에 모은 enum.
 * 코드는 {@code 도메인-일련번호} 형식이며, 새 도메인은 새 그룹을 추가한다.
 * 예외를 던지거나 응답을 만드는 일은 하지 않는다.
 */
@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    // COMMON
    COMMON_BAD_REQUEST(HttpStatus.BAD_REQUEST, "COMMON-001", "잘못된 요청입니다."),
    COMMON_INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "COMMON-002", "서버 내부 오류가 발생했습니다."),
    COMMON_NOT_FOUND(HttpStatus.NOT_FOUND, "COMMON-003", "리소스를 찾을 수 없습니다."),
    COMMON_METHOD_NOT_ALLOWED(HttpStatus.METHOD_NOT_ALLOWED, "COMMON-004", "지원하지 않는 HTTP 메서드입니다.");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;
}
