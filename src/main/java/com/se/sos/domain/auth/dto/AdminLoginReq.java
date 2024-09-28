package com.se.sos.domain.auth.dto;

public record AdminLoginReq(
        String adminId,
        String password
) {

}
