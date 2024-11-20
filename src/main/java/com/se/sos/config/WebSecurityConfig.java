package com.se.sos.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.se.sos.domain.admin.repository.AdminRepository;
import com.se.sos.domain.ambulance.repository.AmbulanceRepository;
import com.se.sos.domain.hospital.repository.HospitalRepository;
import com.se.sos.domain.security.form.filter.FormLoginFilter;
import com.se.sos.domain.user.repository.UserRepository;
import com.se.sos.global.security.JwtAuthenticationFilter;
import com.se.sos.global.security.JwtExceptionFilter;
import com.se.sos.global.util.jwt.JwtUtil;
import com.se.sos.global.util.redis.RedisUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.FormLoginConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfigurationSource;

import static com.se.sos.config.EndpointProperties.*;

@Configuration
@RequiredArgsConstructor
public class WebSecurityConfig {

    private final CorsConfigurationSource corsConfigurationSource;
    private final AuthenticationConfiguration authenticationConfiguration;
    private final JwtUtil jwtUtil;
    private final RedisUtil redisUtil;
    private final ObjectMapper objectMapper;

    private final AccessDeniedHandler accessDeniedHandler;
    private final AuthenticationEntryPoint authenticationEntryPoint;

    private final UserRepository userRepository;
    private final HospitalRepository hospitalRepository;
    private final AmbulanceRepository ambulanceRepository;
    private final AdminRepository adminRepository;

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {


        http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(cors -> cors.configurationSource(corsConfigurationSource))
                .formLogin(FormLoginConfigurer::disable)
                .logout(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorizeHttpRequest -> {

                        /* Anonymous */
                        authorizeHttpRequest
                                .requestMatchers(PUBLIC_EP).permitAll();

                        /* Admin */
                        authorizeHttpRequest
                                .requestMatchers(ADMIN_EP).hasRole("ADMIN");

                        /* Amb */
                        authorizeHttpRequest
                                .requestMatchers(AMB_EP).hasRole("AMB")
                                .requestMatchers(HttpMethod.GET, "/hospital/{hospitalId}").hasAnyRole("AMB","HOS")
                                .requestMatchers(HttpMethod.GET, "/hospital").hasAnyRole("AMB", "USER");

                        /* Hospital */
                        authorizeHttpRequest
                                .requestMatchers(HOS_EP).hasRole("HOS")
                                .requestMatchers(HttpMethod.PUT, "/hospital/{hospitalId}").hasRole("HOS")
                                .requestMatchers(HttpMethod.GET, "/reception/{receptionId}").hasAnyRole("HOS","USER");

                        /* Any Request needed to authenticated */
                        authorizeHttpRequest
                                .anyRequest().authenticated();
                })
                .addFilterAt(new FormLoginFilter(authenticationManager(authenticationConfiguration), jwtUtil, redisUtil, objectMapper), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(new JwtAuthenticationFilter(jwtUtil, userRepository, ambulanceRepository, hospitalRepository, adminRepository), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(new JwtExceptionFilter(objectMapper), JwtAuthenticationFilter.class)
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .exceptionHandling(exceptionConfig ->
                        exceptionConfig.authenticationEntryPoint(authenticationEntryPoint).accessDeniedHandler(accessDeniedHandler)
                )
        ;


        return http.build();
    }
}
