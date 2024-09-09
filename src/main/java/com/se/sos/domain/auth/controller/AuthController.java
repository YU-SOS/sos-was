package com.se.sos.domain.auth.controller;

import com.se.sos.domain.auth.dto.AmbulanceSignupReq;
import com.se.sos.domain.auth.dto.HospitalSignupReq;
import com.se.sos.domain.auth.dto.UserSignupReq;
import com.se.sos.domain.auth.service.AuthService;
import com.se.sos.global.response.success.SuccessRes;
import com.se.sos.global.response.success.SuccessType;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signup/ambulance")
    public ResponseEntity<?> signup(@RequestBody AmbulanceSignupReq ambulanceSignupReq) {
        authService.signupForAmbulance(ambulanceSignupReq);
        return SuccessRes.from(SuccessType.CREATED);
    }

    @PostMapping("/signup/hospital")
    public ResponseEntity<?> signup(@RequestBody HospitalSignupReq hospitalSignupReq) {
        authService.signupForHospital(hospitalSignupReq);
        return SuccessRes.from(SuccessType.CREATED);
    }

    @PostMapping("/login/user")
    public ResponseEntity<?> login(@RequestBody UserSignupReq userSignupReq) {
        return authService.loginForUser(userSignupReq);
    }
}
