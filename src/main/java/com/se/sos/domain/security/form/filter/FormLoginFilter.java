package com.se.sos.domain.security.form.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.se.sos.domain.security.form.dto.CustomUserDetails;
import com.se.sos.domain.user.entity.Role;
import com.se.sos.global.response.error.ErrorRes;
import com.se.sos.global.response.error.ErrorType;
import com.se.sos.global.response.success.SuccessRes;
import com.se.sos.global.response.success.SuccessType;
import com.se.sos.global.util.jwt.JwtUtil;
import com.se.sos.global.util.redis.RedisProperties;
import com.se.sos.global.util.redis.RedisUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
public class FormLoginFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final RedisUtil redisUtil;
    private final ObjectMapper objectMapper;

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            String requestBody = StreamUtils.copyToString(request.getInputStream(), StandardCharsets.UTF_8);

            Map<String, String> loginInfo = objectMapper.readValue(requestBody, Map.class);

            String role = (String) loginInfo.get("role");
            String id = (String) loginInfo.get("id");
            String password = (String) loginInfo.get("password");

            String username = role + " " + id;

            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(username, password);

            return authenticationManager.authenticate(authToken);

        } catch ( IOException e ) {
            log.error("error message : " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    public void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {

        CustomUserDetails userDetails = (CustomUserDetails) authResult.getPrincipal();

        String role = userDetails.getAuthorities().iterator().next().getAuthority();

        String id = userDetails.getId(); // 고유 번호

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        if(role.equals(Role.AMB_GUEST.getValue()) || role.equals(Role.HOS_GUEST.getValue())){
            setResponse(response, ErrorType.GUEST);
            return;
        }

        if(userDetails.hasRole(Role.BLACKLIST)){
            setResponse(response, ErrorType.BLACKLIST);
            return;
        }

        String accessToken = jwtUtil.generateAccessToken(id, role);
        String refreshToken = jwtUtil.generateRefreshToken(id, role);

        response.setHeader("Authorization", accessToken);
        response.addCookie(createCookie("refreshToken", refreshToken));

        response.setStatus(HttpServletResponse.SC_OK);
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(objectMapper.writeValueAsString(SuccessRes.from(SuccessType.LOGIN_SUCCESS)));

        redisUtil.save(RedisProperties.REFRESH_TOKEN_PREFIX+id, refreshToken, jwtUtil.getRefreshTokenDuration());
    }

    @Override
    public void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        ErrorType error = ErrorType.LOGIN_FAILED;
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        response.setContentType("application/json; charset=utf-8");
        response.getWriter().write(objectMapper.writeValueAsString(ErrorRes.of(error.getStatusCode(), error.getMessage())));
    }

    private Cookie createCookie(String key, String value){
        Cookie cookie = new Cookie(key, value);
        cookie.setMaxAge(60*60*24*14); // = refresh token expiration
        cookie.setSecure(true);
        cookie.setPath("/");
        cookie.setHttpOnly(true);

        return cookie;
    }

    private void setResponse(HttpServletResponse response, ErrorType errorType) throws IOException {
        response.setCharacterEncoding("UTF-8");
        response.setStatus(errorType.getStatusCode());
        response.setContentType("application/json");
        response.getWriter().write(objectMapper.writeValueAsString(ErrorRes.from(errorType)));
    }
}
