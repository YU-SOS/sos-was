package com.se.sos.global.util.logging;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

@Aspect
@Component
@Slf4j
@RequiredArgsConstructor
@Profile("prod")
public class RequestLoggingAspect {
    private final HttpServletRequest request;

    @Before("execution(* com.se.sos..*Controller.*(..))")
    public void logBefore(){

        String requestURI = request.getQueryString() != null ? request.getRequestURI()+ URLDecoder.decode(request.getQueryString(), StandardCharsets.UTF_8) : request.getRequestURI();

        log.info("Request Method: {}, Request URI: {}", request.getMethod(), requestURI );
    }
}
