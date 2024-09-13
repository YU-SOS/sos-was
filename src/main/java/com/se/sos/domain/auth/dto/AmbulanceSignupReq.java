package com.se.sos.domain.auth.dto;

import com.se.sos.domain.ambulance.entity.Ambulance;
import com.se.sos.domain.ambulance.entity.Location;
import com.se.sos.global.common.role.Role;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Getter
public class AmbulanceSignupReq extends SignupReq {

    @NotNull
    private Location location;
    @NotBlank
    private String imageUrl;

    public AmbulanceSignupReq(String id, String password, String name, String address, String telephoneNumber, Location location, String imageUrl) {
        super(id, password, name, address, telephoneNumber);
        this.location = location;
        this.imageUrl = imageUrl;
    }

    public static Ambulance toEntity(AmbulanceSignupReq ambulanceSignupReq, String encodedPassword) {
        return Ambulance.builder()
                        .name(ambulanceSignupReq.getName())
                        .address(ambulanceSignupReq.getAddress())
                        .telephoneNumber(ambulanceSignupReq.getTelephoneNumber())
                        .ambulanceId(ambulanceSignupReq.getId())
                        .password(encodedPassword)
                        .role(Role.AMB_GUEST)
                        .location(ambulanceSignupReq.location)
                        .imageUrl(ambulanceSignupReq.getImageUrl())
                        .build();
    }
}
