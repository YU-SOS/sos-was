package com.se.sos.domain.ambulance.dto;

import com.se.sos.domain.ambulance.entity.Ambulance;
import com.se.sos.domain.ambulance.entity.Location;
import lombok.Builder;

import java.util.UUID;

@Builder
public record AmbulanceRegRes(
        UUID id,
        String name,
        String address,
        String telephoneNumber,
        String ambulanceId,
        String password,
        Location location,
        String imageUrl
){
    public static AmbulanceRegRes fromEntity(Ambulance ambulance){
        return AmbulanceRegRes.builder()
                .id(ambulance.getId())
                .name(ambulance.getName())
                .address(ambulance.getAddress())
                .telephoneNumber(ambulance.getTelephoneNumber())
                .ambulanceId(ambulance.getAmbulanceId())
                .password(ambulance.getPassword())
                .location(ambulance.getLocation())
                .imageUrl(ambulance.getImageUrl())
                .build();
    }
}
