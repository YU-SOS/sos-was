package com.se.sos.domain.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.se.sos.global.response.error.ErrorRes;
import com.se.sos.global.response.error.ErrorType;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class JwtExceptionFilter extends OncePerRequestFilter {

    Logger logger = LoggerFactory.getLogger(this.getClass());
    ObjectMapper objectMapper =  new ObjectMapper();


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        ErrorType error = null;

        try{
            filterChain.doFilter(request, response);
        } catch (ExpiredJwtException e){
            logger.error("token expired");
            error = ErrorType.TOKEN_EXPIRED;
            // response setStatus, setContentType, setChracterEncoding ?
            response.getWriter().write(objectMapper.writeValueAsString(ErrorRes.of(error.getStatusCode(), e.getMessage())));
        } catch (JwtException e){
            logger.error("token authentication failed : " + e.getMessage()); // e출력?
            error = ErrorType.UN_AUTHENTICATION;
            response.getWriter().write(objectMapper.writeValueAsString(ErrorRes.of(error.getStatusCode(), e.getMessage())));
        } // finally는 catch로 에러가 안 잡히더라도 실행이 되는건가?
    }
}
