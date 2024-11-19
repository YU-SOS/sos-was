package com.se.sos.domain.auth.controller;

import com.se.sos.domain.auth.api.AuthAPI;
import com.se.sos.domain.auth.dto.*;
import com.se.sos.domain.auth.service.AuthService;
import com.se.sos.global.exception.CustomException;
import com.se.sos.global.response.error.ErrorRes;
import com.se.sos.global.response.error.ErrorType;
import com.se.sos.global.response.success.SuccessRes;
import com.se.sos.global.response.success.SuccessType;
import com.se.sos.global.util.jwt.JwtUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
public class AuthController implements AuthAPI {

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
        TokenDto token = authService.loginForUser(userSignupReq);

        return ResponseEntity.ok()
                .header("Authorization", token.accessToken())
                .header("Set-Cookie", token.refreshToken())
                .body(SuccessRes.from(SuccessType.LOGIN_SUCCESS));

    }

    @PostMapping("/login/admin")
    public ResponseEntity<?> loginForAdmin(@Valid @RequestBody AdminLoginReq adminLoginReq) {
        return ResponseEntity.ok()
                .header("Authorization", authService.loginForAdmin(adminLoginReq))
                .body(SuccessRes.from(SuccessType.LOGIN_SUCCESS));
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestHeader("Authorization") String authorizationHeader){
        String token = jwtUtil.resolveToken(authorizationHeader);

        if(authService.logoutForUser(token))
            return ResponseEntity.ok()
                    .body(SuccessRes.from(SuccessType.LOGOUT_SUCCESS));
        else
            return ResponseEntity.badRequest()
                    .body(ErrorRes.from(ErrorType.LOGOUT_FAILED));
    }

    @GetMapping("/reissue-token")
    public ResponseEntity<?> reissueToken(@CookieValue("refreshToken") String refreshToken){
        if(refreshToken == null)
            throw new CustomException(ErrorType.REFRESH_TOKEN_NOT_FOUND);

        TokenDto token = authService.reissueToken(refreshToken);

        if(token == null)
            return ResponseEntity.badRequest()
                    .body(ErrorRes.from(ErrorType.REISSUE_TOKEN_FAILED));

        return ResponseEntity.ok()
                .header("Authorization", token.accessToken())
                .header("Set-Cookie", token.refreshToken())
                .body(SuccessRes.from(SuccessType.REISSUE_TOKEN_SUCCESS));
    }

    @GetMapping("/dup-check")
    public ResponseEntity<?> dupCheck(
            @RequestParam(name = "id", required = true) String id,
            @RequestParam(name = "role", required = true) String role
    ){
        if(!(role.equals("AMB") || role.equals("HOS")))
            throw new CustomException(ErrorType.BAD_REQUEST);

        if(role.equals("AMB") && !authService.dupCheckAmbulanceId(id))
            return ResponseEntity.ok().body(SuccessRes.from(SuccessType.AVAILABLE_ID));

        if(role.equals("HOS") && !authService.dupCheckHospitalId(id))
            return ResponseEntity.ok().body(SuccessRes.from(SuccessType.AVAILABLE_ID));

        return ResponseEntity.status(ErrorType.ALREADY_USED_ID.getStatus())
                .body(ErrorRes.from(ErrorType.ALREADY_USED_ID));
    }
}
