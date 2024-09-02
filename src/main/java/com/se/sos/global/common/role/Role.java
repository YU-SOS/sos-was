package com.se.sos.global.common.role;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Role {

    AMB("ROLE_AMB"),
    AMB_GUEST("ROLE_AMB_GUEST"),
    HOS("ROLE_HOS"),
    HOS_GUEST("ROLE_HOS_GUEST"),
    ;

    private final String role;
}
