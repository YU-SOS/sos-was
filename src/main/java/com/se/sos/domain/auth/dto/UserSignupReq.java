package com.se.sos.domain.auth.dto;

import com.se.sos.domain.user.entity.User;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record UserSignupReq(
        @NotBlank(message = "이름은 필수 입력값 입니다.")
        String name,
        @NotBlank(message = "소셜 로그인 제공 아이디는 필수 입력값 입니다.")
        String providerId,
        @NotBlank(message = "소셜 로그인 제공자는 필수 입력값 입니다.")
        String provider,
        @Pattern(regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+.[A-Za-z]{2,6}$", message = "이메일 형식에 맞지 않습니다.")
        String email
) {
    public static User toEntity(UserSignupReq req) {
        return User.builder()
                .name(req.name())
                .providerId(req.providerId())
                .provider(req.provider())
                .email(req.email())
                .build();
    }
}
