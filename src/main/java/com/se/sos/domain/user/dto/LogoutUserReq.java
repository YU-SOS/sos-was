package com.se.sos.domain.user.dto;

import com.se.sos.domain.user.entity.User;
import com.se.sos.global.common.role.Role;

public record LogoutUserReq(
        String name,
        String providerUserInfo,
        Role role,
        String email

){
    public static LogoutUserReq fromEntity(User user) {
        return new LogoutUserReq(
                user.getName(),
                user.getProviderUserInfo(),
                user.getRole(),
                user.getEmail()
        );
    }
}