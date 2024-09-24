package com.se.sos.domain.auth.dto;

import com.se.sos.domain.ambulance.entity.Location;
import com.se.sos.domain.hospital.entity.Hospital;
import com.se.sos.domain.user.entity.Role;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class HospitalSignupReq extends SignupReq {

    @NotNull
    private final Location location;
    private final String imageUrl;
//    private final List<String> category;

    public HospitalSignupReq(String id, String password, String name, String address, String telephoneNumber, String imageUrl, Location location) {
        super(id, password, name, address, telephoneNumber);
        this.location = location;
        this.imageUrl = imageUrl;
    }

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
