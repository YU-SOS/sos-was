package com.se.sos.domain.auth.dto;

import com.se.sos.domain.ambulance.entity.Ambulance;
import com.se.sos.domain.hospital.entity.Hospital;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;


@Getter
public class SignupReq {
    private final String id;
    private final String password;
    private final String role;

    @Builder
    public SignupReq(String id, String password, String role) {
        this.id = id;
        this.password = password;
        this.role = role;
    }
}