package com.se.sos.common.response.success;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;

@Getter
@AllArgsConstructor
@RequiredArgsConstructor
public class SuccessRes<T> {
    private final int status;
    private final String message;
    private T data; // 필수 X

    public static ResponseEntity<SuccessRes<?>> from(SuccessType success) {
        return ResponseEntity
                .status(success.getStatus())
                .body(new SuccessRes<>(success.getStatusCode(), success.getMessage()));
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