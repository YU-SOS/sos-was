package com.se.sos.global.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.se.sos.global.response.error.ErrorRes;
import com.se.sos.global.response.error.ErrorType;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class JwtExceptionFilter extends OncePerRequestFilter {

    private final ObjectMapper objectMapper;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        ErrorType error = null;

        try{
            filterChain.doFilter(request, response);
        } catch (ExpiredJwtException e){
            log.error("token expired");
            error = ErrorType.TOKEN_EXPIRED;
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(objectMapper.writeValueAsString(ErrorRes.of(error.getStatusCode(), e.getMessage())));

        } catch (JwtException e){
            log.error("token authentication failed : " + e.getMessage()); // e출력?
            error = ErrorType.UN_AUTHENTICATION;
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(objectMapper.writeValueAsString(ErrorRes.of(error.getStatusCode(), e.getMessage())));

        }
    }
}
