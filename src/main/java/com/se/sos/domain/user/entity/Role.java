package com.se.sos.domain.user.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Role {

    AMB("ROLE_AMB"),
    AMB_GUEST("ROLE_AMB_GUEST"),
    HOS("ROLE_HOS"),
    HOS_GUEST("ROLE_HOS_GUEST"),
    USER("ROLE_USER"),
    ADMIN("ROLE_ADMIN")
    ;

    private final String role;
}
