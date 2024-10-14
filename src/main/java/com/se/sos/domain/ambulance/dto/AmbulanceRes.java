package com.se.sos.domain.ambulance.dto;

import com.se.sos.domain.ambulance.entity.Ambulance;
import com.se.sos.domain.ambulance.entity.Location;

import java.util.UUID;

public record AmbulanceRes(
        UUID id,
        String name,
        String address,
        String telephoneNumber,
        Location location,
        String imageUrl
) {
    public static AmbulanceRes from(Ambulance ambulance) {
        return new AmbulanceRes(
                ambulance.getId(),
                ambulance.getName(),
                ambulance.getAddress(),
                ambulance.getTelephoneNumber(),
                ambulance.getLocation(),
                ambulance.getImageUrl()
        );
    }
}
