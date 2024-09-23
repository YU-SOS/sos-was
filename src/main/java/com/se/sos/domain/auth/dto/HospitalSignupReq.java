package com.se.sos.domain.auth.dto;

import com.se.sos.domain.ambulance.entity.Location;
import com.se.sos.domain.hospital.entity.Hospital;
import com.se.sos.global.common.role.Role;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class HospitalSignupReq extends SignupReq {

    @NotNull
    private Location location;
    private String imageUrl;
    private List<String> categories;


    public static Hospital toEntity(HospitalSignupReq hospitalSignupReq, String encodedPassword) {
        return Hospital.builder()
                .hospitalId(hospitalSignupReq.getId())
                .password(encodedPassword)
                .role(Role.HOS_GUEST)
                .name(hospitalSignupReq.getName())
                .address(hospitalSignupReq.getAddress())
                .telephoneNumber(hospitalSignupReq.getTelephoneNumber())
                .imageUrl(hospitalSignupReq.getImageUrl())
                .location(hospitalSignupReq.getLocation())
                .build();
    }
}
