package com.se.sos.exception;

import com.se.sos.common.response.error.ErrorType;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CustomException extends RuntimeException {
    private final ErrorType errorType;
}
