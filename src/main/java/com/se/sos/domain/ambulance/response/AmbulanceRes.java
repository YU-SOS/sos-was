package com.se.sos.domain.ambulance.response;

import com.se.sos.domain.ambulance.entity.Ambulance;
import com.se.sos.domain.ambulance.entity.Location;
import com.se.sos.domain.user.entity.Role;

import java.util.UUID;

public record AmbulanceRes(
        UUID id,
        String name,
        String address,
        String telephoneNumber,
        String ambulanceId,
        Role role,
        Location location,
        String imageUrl
) {
    public static AmbulanceRes from(Ambulance ambulance) {
        return new AmbulanceRes(
                ambulance.getId(),
                ambulance.getName(),
                ambulance.getAddress(),
                ambulance.getTelephoneNumber(),
                ambulance.getAmbulanceId(),
                ambulance.getRole(),
                ambulance.getLocation(),
                ambulance.getImageUrl()
        );
    }
}
