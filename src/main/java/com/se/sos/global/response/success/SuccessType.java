package com.se.sos.global.response.success;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum SuccessType {

    // 200
    OK(HttpStatus.OK, "요청이 성공했습니다."),

    // 201
    CREATED(HttpStatus.CREATED, "등록을 성공하였습니다."),
    USER_CREATED(HttpStatus.CREATED, "사용자 등록을 성공하였습니다."),
    COMMENT_CREATED(HttpStatus.CREATED, "코멘트 등록을 성공하였습니다.");

    private final HttpStatus status;
    private final String message;

    public int getStatusCode(){
        return status.value();
    }
}
