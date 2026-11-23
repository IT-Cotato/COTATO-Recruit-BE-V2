package com.cotato.recruit.global.common.response;

import lombok.experimental.UtilityClass;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 * {@link ApiResponse} 를 {@code ResponseEntity} 로 감싸는 컨트롤러용 유틸.
 * 상태 코드는 200(ok), 201(created) 두 가지만 다룬다.
 */
@UtilityClass
public class ResponseUtils {

    public <T> ResponseEntity<ApiResponse<T>> ok(T data) {
        return ResponseEntity.ok(ApiResponse.success(data));
    }

    public <T> ResponseEntity<ApiResponse<T>> created(T data) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.created(data));
    }
}
