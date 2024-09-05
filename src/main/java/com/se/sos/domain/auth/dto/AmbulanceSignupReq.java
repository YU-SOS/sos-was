package com.se.sos.domain.auth.dto;

import com.se.sos.domain.ambulance.entity.Ambulance;
import com.se.sos.global.common.role.Role;
import lombok.Builder;
import lombok.Getter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Getter
public class AmbulanceSignupReq extends SignupReq {

    public AmbulanceSignupReq(String id, String password,String name, String address, String telephoneNumber) {
        super(id, password, name, address, telephoneNumber);
    }

    public static Ambulance toEntity(AmbulanceSignupReq ambulanceSignupReq, String encodedPassword) {
        return Ambulance.builder()
                        .ambulanceId(ambulanceSignupReq.getId())
                        .password(encodedPassword)
                        .role(Role.AMB_GUEST)
                        .build();
    }
}
