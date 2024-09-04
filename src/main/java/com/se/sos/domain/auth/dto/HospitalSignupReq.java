package com.se.sos.domain.auth.dto;

import com.se.sos.domain.hospital.entity.Hospital;
import com.se.sos.global.common.role.Role;
import lombok.Getter;

import java.util.List;

@Getter
public class HospitalSignupReq extends SignupReq {

    private final String logitude;
    private final String latitude;
    private final String imageUrl;
    private final List<String> category;

    public HospitalSignupReq(String id, String password, String name, String address, String telephoneNumber, String latitude, String logitude, String imageUrl, List<String> category) {
        super(id, password, name, address, telephoneNumber);
        this.logitude = logitude;
        this.latitude = latitude;
        this.imageUrl = imageUrl;
        this.category = category;
    }

    public static Hospital toEntity(HospitalSignupReq hospitalSignupReq, String encodedPassword) {
        return Hospital.builder()
                .hospitalId(hospitalSignupReq.getId())
                .password(encodedPassword)
                .role(Role.HOS_GUEST)
                .build();
    }
}
