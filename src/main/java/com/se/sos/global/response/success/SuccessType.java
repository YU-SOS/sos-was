package com.se.sos.global.response.success;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum SuccessType {

    // 200
    OK(HttpStatus.OK, "요청이 성공했습니다."),
    LOGIN_SUCCESS(HttpStatus.OK, "로그인에 성공하였습니다."),
    LOGOUT_SUCCESS(HttpStatus.OK, "로그아웃 성공하였습니다."),
    REISSUE_TOKEN_SUCCESS(HttpStatus.OK, "토큰 재발급에 성공하였습니다."),
    AVAILABE_ID(HttpStatus.OK, "사용가능한 아이디입니다."),

    // 201
    CREATED(HttpStatus.CREATED, "등록을 성공하였습니다."),
    USER_CREATED(HttpStatus.CREATED, "사용자 회원가입을 성공하였습니다."),
    AMBULANCE_CREATED(HttpStatus.CREATED, "구급대 회원가입을 성공하였습니다."),
    HOSPITAL_CREATED(HttpStatus.CREATED, "병원 회원가입을 성공하였습니다."),
    COMMENT_CREATED(HttpStatus.CREATED, "코멘트 등록을 성공하였습니다.");

    private final HttpStatus status;
    private final String message;

    public int getStatusCode(){
        return status.value();
    }
}
