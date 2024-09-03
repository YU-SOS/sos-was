package com.se.sos.global.exception;

import com.se.sos.global.response.error.ErrorType;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CustomException extends RuntimeException {
    private final ErrorType errorType;
}
