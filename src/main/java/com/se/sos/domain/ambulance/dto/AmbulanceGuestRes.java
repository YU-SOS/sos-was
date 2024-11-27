package com.se.sos.domain.ambulance.dto;

import com.se.sos.domain.ambulance.entity.Ambulance;
import lombok.Builder;

@Builder
public record AmbulanceGuestRes(
        String name,
        String address,
        String telephoneNumber,
        String imageUrl
) {
    public static AmbulanceGuestRes fromEntity(Ambulance ambulance) {
        return AmbulanceGuestRes.builder()
                .name(ambulance.getName())
                .address(ambulance.getAddress())
                .imageUrl(ambulance.getImageUrl())
                .telephoneNumber(ambulance.getTelephoneNumber())
                .build();
    }
}
