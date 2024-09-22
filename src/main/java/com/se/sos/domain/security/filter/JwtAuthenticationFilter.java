package com.se.sos.domain.security.filter;

import com.se.sos.domain.ambulance.entity.Ambulance;
import com.se.sos.domain.ambulance.repository.AmbulanceRepository;
import com.se.sos.domain.hospital.entity.Hospital;
import com.se.sos.domain.hospital.repository.HospitalRepository;
import com.se.sos.domain.security.form.dto.AmbulanceDetails;
import com.se.sos.domain.security.form.dto.HospitalDetails;
import com.se.sos.domain.security.form.dto.SecurityUserDetails;
import com.se.sos.domain.user.entity.User;
import com.se.sos.domain.user.repository.UserRepository;
import com.se.sos.global.common.role.Role;
import com.se.sos.global.exception.CustomException;
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


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String authorization = request.getHeader(HttpHeaders.AUTHORIZATION);

        if(authorization == null || !authorization.startsWith("Bearer ")) {
            log.warn("TOKEN NOT FOUND or TOKEN HEADER IS WRONG");
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
        String role = "ROLE_"+ claims.get("role").toString();

        UserDetails userDetails = null;

        if(role.equals(Role.AMB.getRole()) || role.equals(Role.AMB_GUEST.getRole())){
            Ambulance ambulance = ambulanceRepository.findById(id)
                    .orElseThrow(() -> new CustomException(ErrorType.AMBULANCE_NOT_FOUND)); // not have handler

            userDetails = new AmbulanceDetails(ambulance);
        } else if(role.equals(Role.HOS.getRole()) || role.equals(Role.HOS_GUEST.getRole())){
            Hospital hospital = hospitalRepository.findById(id)
                    .orElseThrow(() -> new CustomException(ErrorType.HOSPITAL_NOT_FOUND));

            userDetails = new HospitalDetails(hospital);
        } else if(role.equals(Role.USER.getRole())){
            User user = userRepository.findById(id)
                    .orElseThrow(() -> new CustomException(ErrorType.USER_NOT_FOUND));

            userDetails = new SecurityUserDetails(user);
        } else {
            return null;
        }

        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

    }
}
