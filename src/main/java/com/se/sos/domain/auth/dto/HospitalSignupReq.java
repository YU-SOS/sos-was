package com.se.sos.domain.auth.dto;

import com.se.sos.domain.hospital.entity.Hospital;
import com.se.sos.global.common.role.Role;
import lombok.Getter;

@Getter
public class HospitalSignupReq extends SignupReq {

    private String name;
    private String address;
    private String telephoneNumber;

    public HospitalSignupReq(String id, String password, String role, String name, String address, String telephoneNumber) {
        super(id, password, role);
        this.name = name;
        this.address = address;
        this.telephoneNumber = telephoneNumber;
    }

    public static Hospital toEntity(HospitalSignupReq hospitalSignupReq, String encodedPassword) {
        return Hospital.builder()
                .hospitalId(hospitalSignupReq.getId())
                .password(encodedPassword)
                .role(Role.HOS_GUEST)
                .build();
    }
}
