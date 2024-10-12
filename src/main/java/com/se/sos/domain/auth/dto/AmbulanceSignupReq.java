package com.se.sos.domain.auth.dto;

import com.se.sos.domain.ambulance.entity.Ambulance;
import com.se.sos.domain.ambulance.entity.Location;
import com.se.sos.domain.user.entity.Role;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AmbulanceSignupReq extends SignupReq {

    @Valid
    private  Location location;
    private  String imageUrl;

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
