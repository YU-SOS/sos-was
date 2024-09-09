package com.se.sos.domain.auth.dto;

import com.se.sos.domain.user.entity.User;

public record UserSignupReq(
        String name,
        String providerId,
        String provider,
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
