package com.se.sos.common.response.success;

import org.springframework.http.ResponseEntity;

public record SuccessRes<T> (
    int status,
    String message,
    T data // 필수 X
){
    public static ResponseEntity<SuccessRes<?>> from(SuccessType success) {
        return ResponseEntity
                .status(success.getStatus())
                .body(new SuccessRes<>(success.getStatusCode(), success.getMessage(), null));
    }

    public static <T> ResponseEntity<SuccessRes<T>> from(T data) {
        return ResponseEntity
                .status(SuccessType.OK.getStatus())
                .body(new SuccessRes<>(SuccessType.OK.getStatusCode(), SuccessType.OK.getMessage(), data));
    }

    public static <T> ResponseEntity<SuccessRes<T>> from(SuccessType success, T data) {
        return ResponseEntity
                .status(success.getStatus())
                .body(new SuccessRes<>(success.getStatusCode(), success.getMessage(), data));
    }
}