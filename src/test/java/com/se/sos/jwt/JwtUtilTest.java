package com.se.sos.jwt;


import com.se.sos.global.util.jwt.JwtUtil;
import io.jsonwebtoken.Claims;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
public class JwtUtilTest {

    @Autowired
    JwtUtil jwtUtil;

    @Test
    @DisplayName("jwt 토큰 생성 예제")
    void 토큰_성생(){
        String userId = "testId";
        String role = "ROLE_USER";

        String token = jwtUtil.generateAccessToken(userId, role);

        Claims claims = jwtUtil.parseToken(token);



    }
}
