package com.se.sos.common.response.error;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorType {

    // 인증
    // 401
    UN_AUTHENTICATION(HttpStatus.UNAUTHORIZED, "인증이 실패되었습니다."),
    TOKEN_EXPIRED(HttpStatus.UNAUTHORIZED, "토큰이 만료되었습니다."),
    INVALID_TOKEN(HttpStatus.UNAUTHORIZED, "인증되지 않은 토큰입니다."),
    SOCIAL_LOGIN_FAILED(HttpStatus.UNAUTHORIZED, "소셜 로그인 실패입니다."),

    // 인가
    // 403
    UN_AUTHORIZATION(HttpStatus.FORBIDDEN, "허용되지 않은 접근입니다."),

    // 데이터
    BAD_REQUEST(HttpStatus.BAD_REQUEST, "유효성 검증 실패. 잘못된 요청입니다."), // 400
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 사용자입니다."),        // 404
    AMBULANCE_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 구급대입니다."),
    HOSPITAL_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 병원입니다."),
    RECEPTION_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 접수 입니다."),


    // 서버 에러
    // 500
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "내부 서버 에러. 서버 팀으로 연락주시기 바랍니다.");

    private final HttpStatus status;
    private final String message;

    public int getStatusCode(){
        return status.value();
    }
}