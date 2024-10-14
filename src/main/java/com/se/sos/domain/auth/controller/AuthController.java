package com.se.sos.domain.auth.controller;

import com.se.sos.domain.auth.dto.AdminLoginReq;
import com.se.sos.domain.auth.dto.AmbulanceSignupReq;
import com.se.sos.domain.auth.dto.HospitalSignupReq;
import com.se.sos.domain.auth.dto.UserSignupReq;
import com.se.sos.domain.auth.service.AuthService;
import com.se.sos.global.exception.CustomException;
import com.se.sos.global.response.error.ErrorType;
import com.se.sos.global.response.success.SuccessRes;
import com.se.sos.global.response.success.SuccessType;
import com.se.sos.global.util.jwt.JwtUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final JwtUtil jwtUtil;

    @PostMapping("/signup/ambulance")
    public ResponseEntity<?> signup(@Valid @RequestBody AmbulanceSignupReq ambulanceSignupReq) {
        authService.signupForAmbulance(ambulanceSignupReq);
        return ResponseEntity
                .status(SuccessType.AMBULANCE_CREATED.getStatus())
                .body(SuccessRes.from(SuccessType.AMBULANCE_CREATED));
    }

    @PostMapping("/signup/hospital")
    public ResponseEntity<?> signup(@Valid @RequestBody HospitalSignupReq hospitalSignupReq) {
        authService.signupForHospital(hospitalSignupReq);
        return ResponseEntity
                .status(SuccessType.HOSPITAL_CREATED.getStatus())
                .body(SuccessRes.from(SuccessType.HOSPITAL_CREATED));
    }

    @PostMapping("/login/user")
    public ResponseEntity<?> login(@Valid @RequestBody UserSignupReq userSignupReq) {
        return authService.loginForUser(userSignupReq);
    }

    @PostMapping("/login/admin")
    public ResponseEntity<?> loginForAdmin(@Valid @RequestBody AdminLoginReq adminLoginReq) {
        return authService.loginForAdmin(adminLoginReq);
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestHeader("Authorization") String authorizationHeader){
        String token = jwtUtil.resolveToken(authorizationHeader);
        return authService.logoutForUser(token);
    }

    @GetMapping("/reissue-token")
    public ResponseEntity<?> reissueToken(@CookieValue("refreshToken") String refreshToken){
        if(refreshToken == null)
            throw new CustomException(ErrorType.REFRESH_TOKEN_NOT_FOUND);

        return authService.reissueToken(refreshToken);
    }

    @GetMapping("/dup-check")
    public ResponseEntity<?> dupCheck(@RequestParam(name = "id")String id, @RequestParam(name = "role")String role){
        if(id.isBlank() || role.isBlank())
            throw new CustomException(ErrorType.BAD_REQUEST);

        return authService.dupCheck(id, role);
    }
}
