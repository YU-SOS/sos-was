package com.se.sos.domain.auth.dto;

import com.se.sos.domain.ambulance.entity.Ambulance;
import com.se.sos.global.common.role.Role;
import lombok.Builder;
import lombok.Getter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Getter
public class AmbulanceSignupReq extends SignupReq {


    private String name;
    private String address;
    private String telephoneNumber;

    public AmbulanceSignupReq(String id, String password, String role, String name, String address, String telephoneNumber) {
        super(id, password, role); // 부모 클래스의 생성자 호출
        this.name = name;
        this.address = address;
        this.telephoneNumber = telephoneNumber;
    }

    public static Ambulance toEntity(AmbulanceSignupReq ambulanceSignupReq, String encodedPassword) {
        return Ambulance.builder()
                        .ambulanceId(ambulanceSignupReq.getId())
                        .password(encodedPassword)
                        .role(Role.AMB_GUEST)
                        .build();
    }
}
