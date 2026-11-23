package com.cotato.recruit.global.exception;

import lombok.Getter;

/**
 * 프로젝트에서 던지는 공통 예외. {@link ErrorCode} 를 담아 전역 핸들러가 응답으로 바꾸게 한다.
 * 상황마다 새 예외 클래스를 만들지 않고 이 예외에 ErrorCode 만 바꿔서 쓴다.
 */
@Getter
public class RecruitException extends RuntimeException {

    private final ErrorCode errorCode;

    public RecruitException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public RecruitException(ErrorCode errorCode, String customMessage) {
        super(customMessage != null ? customMessage : errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public RecruitException(ErrorCode errorCode, Throwable cause) {
        super(errorCode.getMessage(), cause);
        this.errorCode = errorCode;
    }
}
