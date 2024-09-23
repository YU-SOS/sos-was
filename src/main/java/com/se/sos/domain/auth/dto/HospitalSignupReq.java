package com.se.sos.domain.auth.dto;

import com.se.sos.domain.ambulance.entity.Location;
import com.se.sos.domain.hospital.entity.Hospital;
import com.se.sos.global.common.role.Role;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.util.List;

@Getter
public class HospitalSignupReq extends SignupReq {

    @NotNull
    private Location location;
    private String imageUrl;
    private List<String> categories;


    public HospitalSignupReq() {
    }

    public HospitalSignupReq(String id, String password, String name, String address, String telephoneNumber, String imageUrl, Location location, List<String> categories) {
        super(id, password, name, address, telephoneNumber);
        this.location = location;
        this.imageUrl = imageUrl;
        this.categories = categories;
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
