package com.se.sos.global.security;

import com.se.sos.domain.admin.repository.AdminRepository;
import com.se.sos.domain.ambulance.repository.AmbulanceRepository;
import com.se.sos.domain.hospital.repository.HospitalRepository;
import com.se.sos.domain.security.form.dto.AdminDetails;
import com.se.sos.domain.security.form.dto.AmbulanceDetails;
import com.se.sos.domain.security.form.dto.HospitalDetails;
import com.se.sos.domain.security.form.dto.SecurityUserDetails;
import com.se.sos.domain.user.entity.Role;
import com.se.sos.domain.user.repository.UserRepository;
import com.se.sos.global.response.error.ErrorType;
import com.se.sos.global.util.jwt.JwtUtil;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;
    private final AmbulanceRepository ambulanceRepository;
    private final HospitalRepository hospitalRepository;
    private final AdminRepository adminRepository;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String authorization = request.getHeader(HttpHeaders.AUTHORIZATION);

        if(authorization == null || !authorization.startsWith("Bearer ")) {
            log.warn("TOKEN NOT FOUND or TOKEN HEADER IS WRONG : " + authorization);
        } else {
            String accessToken = authorization.split(" ")[1];

            Authentication authToken = getAuthentication(accessToken);
            SecurityContextHolder.getContext().setAuthentication(authToken);
        }
        filterChain.doFilter(request, response);
    }

    private Authentication getAuthentication(String token){
        Claims claims = jwtUtil.parseToken(token); // throws Error
        UUID id = UUID.fromString(claims.getSubject());
        String role = claims.get("role").toString();

        UserDetails userDetails = null;

        if (role.equals(Role.AMB.getValue()) || role.equals(Role.AMB_GUEST.getValue())) {
            userDetails = ambulanceRepository.findById(id)
                    .map(AmbulanceDetails::new)
                    .orElseGet(() -> {
                        log.error(ErrorType.AMBULANCE_NOT_FOUND.getMessage());
                        return null;
                    });
        } else if (role.equals(Role.HOS.getValue()) || role.equals(Role.HOS_GUEST.getValue())) {
            userDetails = hospitalRepository.findById(id)
                    .map(HospitalDetails::new)
                    .orElseGet(() -> {
                        log.error(ErrorType.HOSPITAL_NOT_FOUND.getMessage());
                        return null;
                    });
        } else if (role.equals(Role.USER.getValue())) {
            userDetails = userRepository.findById(id)
                    .map(SecurityUserDetails::new)
                    .orElseGet(() -> {
                        log.error(ErrorType.USER_NOT_FOUND.getMessage());
                        return null;
                    });
        } else if(role.equals(Role.ADMIN.getValue())){
            userDetails = adminRepository.findById(id)
                    .map(AdminDetails::new)
                    .orElseGet(() -> {
                        log.error(ErrorType.ADMIN_NOT_FOUND.getMessage());
                        return null;
                    });
        }

        return userDetails == null ? null : new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

    }
}
