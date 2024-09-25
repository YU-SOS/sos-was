package com.se.sos.domain.auth.service;

import com.se.sos.domain.ambulance.entity.Ambulance;
import com.se.sos.domain.ambulance.repository.AmbulanceRepository;
import com.se.sos.domain.auth.dto.AmbulanceSignupReq;
import com.se.sos.domain.auth.dto.HospitalSignupReq;
import com.se.sos.domain.auth.dto.UserSignupReq;
import com.se.sos.domain.hospital.entity.Hospital;
import com.se.sos.domain.hospital.repository.HospitalRepository;
import com.se.sos.domain.user.entity.User;
import com.se.sos.domain.user.repository.UserRepository;
import com.se.sos.global.exception.CustomException;
import com.se.sos.global.response.error.ErrorRes;
import com.se.sos.global.response.error.ErrorType;
import com.se.sos.global.response.success.SuccessRes;
import com.se.sos.global.response.success.SuccessType;
import com.se.sos.global.util.cookie.CookieUtil;
import com.se.sos.global.util.jwt.JwtUtil;
import com.se.sos.global.util.redis.RedisProperties;
import com.se.sos.global.util.redis.RedisUtil;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import static com.se.sos.global.response.success.SuccessType.LOGOUT_SUCCESS;


@Service
@RequiredArgsConstructor
public class AuthService {

    private final AmbulanceRepository ambulanceRepository;
    private final HospitalRepository hospitalRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final CookieUtil cookieUtil;
    private final RedisUtil redisUtil;

    public void signupForAmbulance(AmbulanceSignupReq ambulanceSignupReq) {
        if (ambulanceRepository.existsByName(ambulanceSignupReq.getName()))
            throw new CustomException(ErrorType.ALREADY_EXISTS_AMBULANCE);

        Ambulance newAmbulance = AmbulanceSignupReq.toEntity(ambulanceSignupReq, bCryptPasswordEncoder.encode(ambulanceSignupReq.getPassword()));
        ambulanceRepository.save(newAmbulance);
    }

    public void signupForHospital(HospitalSignupReq hospitalSignupReq) {
        if (hospitalRepository.existsByHospitalId(hospitalSignupReq.getName()))
            throw new CustomException(ErrorType.ALREADY_EXISTS_HOSPITAL);

        Hospital newHospital = HospitalSignupReq.toEntity(hospitalSignupReq, bCryptPasswordEncoder.encode(hospitalSignupReq.getPassword()));
        hospitalRepository.save(newHospital);
    }

    public ResponseEntity<?> loginForUser(UserSignupReq req) {
        String providerUserInfo = req.provider() + " " + req.providerId();

        User user = userRepository.findByProviderUserInfo(providerUserInfo);

        if (user == null) { // 가입 X
            User newUser = UserSignupReq.toEntity(req);
            userRepository.save(newUser);
            user = newUser;
        } else {                // 이미 가입
            user.updateName(req.name());
            userRepository.save(user);
        }

        String accessToken = jwtUtil.generateAccessToken(user.getId().toString(), user.getRole().toString());
        String refreshToken = jwtUtil.generateRefreshToken(user.getId().toString(), user.getRole().toString());
        redisUtil.save(RedisProperties.REFRESH_TOKEN_PREFIX + user.getId(), refreshToken, jwtUtil.getRefreshTokenDuration());

        return ResponseEntity.status(SuccessType.USER_CREATED.getStatus())
                .header("Set-Cookie", cookieUtil.addRefreshTokenCookie(refreshToken).toString())
                .header("Authorization", accessToken)
                .body(null);
    }

    public ResponseEntity<?> logoutForUser(String accessToken) {

        String userId = jwtUtil.parseToken(accessToken).getSubject();

        // AccessToken Redis 블랙리스트 추가 (남은 시간 동안)
        long remainingTime = jwtUtil.extractTokenExpirationDate(accessToken).getTime() - System.currentTimeMillis();
        redisUtil.setBlacklistToken(RedisProperties.ACCESS_TOKEN_PREFIX + userId, remainingTime);

        // RefreshToken Redis에서 삭제
        boolean deleteRefreshToken = redisUtil.delete(RedisProperties.REFRESH_TOKEN_PREFIX + userId);

        if (deleteRefreshToken) {
            return ResponseEntity.status(LOGOUT_SUCCESS.getStatus())
                    .header(HttpHeaders.SET_COOKIE, cookieUtil.deleteRefreshTokenCookie().toString())
                    .body(SuccessRes.from(LOGOUT_SUCCESS));
        } else {
            return ResponseEntity.status(ErrorType.LOGOUT_FAILED.getStatus())
                    .body(ErrorRes.from(ErrorType.LOGOUT_FAILED));
        }
    }

    public ResponseEntity<?> reissueToken(String refreshToken){
        if(refreshToken == null){
            throw new CustomException(ErrorType.INVALID_TOKEN);
        }

        refreshToken = refreshToken.replace("Bearer ", "");
        Claims claims = jwtUtil.parseToken(refreshToken);

        String id = claims.getSubject();
        String role = claims.get("role", String.class);
        String key = RedisProperties.REFRESH_TOKEN_PREFIX + id;

        Object existedToken = redisUtil.get(key);

        if(existedToken != null && refreshToken.equals(existedToken.toString())){
            String newAccessToken = jwtUtil.generateAccessToken(id,role);
            String newRefreshToken = jwtUtil.generateRefreshToken(id,role);

            redisUtil.save(key,newRefreshToken, jwtUtil.getRefreshTokenDuration());

            return ResponseEntity
                    .status(SuccessType.REISSUE_TOKEN_SUCCESS.getStatus())
                    .header(HttpHeaders.AUTHORIZATION, newAccessToken)
                    .header(HttpHeaders.SET_COOKIE, cookieUtil.addRefreshTokenCookie(newRefreshToken).toString())
                    .body(SuccessRes.from(SuccessType.REISSUE_TOKEN_SUCCESS));
        } else {
            return ResponseEntity
                    .status(ErrorType.REISSUE_TOKEN_FAILED.getStatus())
                    .body(ErrorRes.from(ErrorType.REISSUE_TOKEN_FAILED));
        }

    }
}
