package com.se.sos.domain.auth.service;

import com.se.sos.domain.admin.entity.Admin;
import com.se.sos.domain.admin.repository.AdminRepository;
import com.se.sos.domain.ambulance.entity.Ambulance;
import com.se.sos.domain.ambulance.repository.AmbulanceRepository;
import com.se.sos.domain.auth.dto.*;
import com.se.sos.domain.category.entity.Category;
import com.se.sos.domain.category.repository.CategoryRepository;
import com.se.sos.domain.hospital.entity.Hospital;
import com.se.sos.domain.hospital.repository.HospitalRepository;
import com.se.sos.domain.user.entity.Role;
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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.se.sos.global.response.success.SuccessType.LOGIN_SUCCESS;
import static com.se.sos.global.response.success.SuccessType.LOGOUT_SUCCESS;


@Service
@RequiredArgsConstructor
public class AuthService {

    private final AmbulanceRepository ambulanceRepository;
    private final HospitalRepository hospitalRepository;
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;
    private final AdminRepository adminRepository;


    private final BCryptPasswordEncoder bCryptPasswordEncoder;
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
        if (hospitalRepository.existsByHospitalId(hospitalSignupReq.getId()))
            throw new CustomException(ErrorType.ALREADY_USED_ID);
        if(hospitalRepository.existsByName(hospitalSignupReq.getName()))
            throw new CustomException(ErrorType.ALREADY_EXISTS_HOSPITAL);

        Hospital newHospital = HospitalSignupReq.toEntity(hospitalSignupReq, bCryptPasswordEncoder.encode(hospitalSignupReq.getPassword()));
        List<Category> categories = categoryRepository.findByNameIn(hospitalSignupReq.getCategories());
        newHospital.addCategories(categories);
        hospitalRepository.save(newHospital);   // 중간테이블까지 업데이트된다.
    }

    public TokenDto loginForUser(UserSignupReq req) {
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

        String accessToken = jwtUtil.generateAccessToken(user.getId().toString(), user.getRole().getValue());
        String refreshToken = jwtUtil.generateRefreshToken(user.getId().toString(), user.getRole().getValue());
        redisUtil.save(RedisProperties.REFRESH_TOKEN_PREFIX + user.getId(), refreshToken, jwtUtil.getRefreshTokenDuration());

        return TokenDto.builder()
                .accessToken(accessToken)
                .refreshToken(cookieUtil.addRefreshTokenCookie(refreshToken).toString())
                .build();
    }

    public boolean logoutForUser(String accessToken) {
        String userId = jwtUtil.parseToken(accessToken).getSubject();

        long remainingTime = jwtUtil.extractTokenExpirationDate(accessToken).getTime() - System.currentTimeMillis();
        redisUtil.setBlacklistToken(RedisProperties.ACCESS_TOKEN_PREFIX + userId, remainingTime);

        return redisUtil.delete(RedisProperties.REFRESH_TOKEN_PREFIX + userId); // 삭제 성공 => true
    }

    public String loginForAdmin(AdminLoginReq adminLoginReq) {

        Admin admin = adminRepository.findByAdminId(adminLoginReq.adminId())
                .orElseThrow(() -> new CustomException(ErrorType.UN_AUTHENTICATION));

        if(admin.getPassword().equals(adminLoginReq.password()))
            return jwtUtil.generateAccessToken(admin.getId().toString(), Role.ADMIN.getValue());
        else
            throw new CustomException(ErrorType.UN_AUTHENTICATION);
    }

    public TokenDto reissueToken(String refreshToken){
        if(refreshToken == null){
            throw new CustomException(ErrorType.REFRESH_TOKEN_NOT_FOUND);
        }

        Claims claims = jwtUtil.parseToken(refreshToken);

        String id = claims.getSubject();
        String role = claims.get("role", String.class);
        String key = RedisProperties.REFRESH_TOKEN_PREFIX + id;

        Object existedToken = redisUtil.get(key);

        if(existedToken != null && refreshToken.equals(existedToken.toString())){
            String newAccessToken = jwtUtil.generateAccessToken(id,role);
            String newRefreshToken = jwtUtil.generateRefreshToken(id,role);
            redisUtil.save(key,newRefreshToken, jwtUtil.getRefreshTokenDuration());

            return TokenDto.builder()
                    .accessToken(newAccessToken)
                    .refreshToken(cookieUtil.addRefreshTokenCookie(newRefreshToken).toString())
                    .build();

        } else
            return null;

    }

    public boolean dupCheckAmbulanceId(String id) {
        return ambulanceRepository.existsByAmbulanceId(id);
    }

    public boolean dupCheckHospitalId(String id){
        return hospitalRepository.existsByHospitalId(id);
    }
}
