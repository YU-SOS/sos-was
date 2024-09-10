package com.se.sos.global.util.cookie;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;

@Component
public class CookieUtil {

    @Value("${cookie.refresh-expiration}")
    private int REFRESH_EXPIRATION;

    public ResponseCookie addRefreshTokenCookie(String value){
        return ResponseCookie.from("refreshToken", value)
                .maxAge(REFRESH_EXPIRATION)
                .secure(false)
                .httpOnly(true)
                .path("/")
                .sameSite("None")
                .build();
    }

    public ResponseCookie deleteRefreshTokenCookie() {
        return ResponseCookie.from("refreshToken", null)
                .maxAge(0)
                .secure(false)
                .httpOnly(true)
                .path("/")
                .build();
    }
}
