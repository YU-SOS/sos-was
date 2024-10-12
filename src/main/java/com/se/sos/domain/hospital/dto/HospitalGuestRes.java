package com.se.sos.domain.hospital.dto;

import com.se.sos.domain.ambulance.entity.Location;
import com.se.sos.domain.hospital.entity.Hospital;
import lombok.Builder;

@Builder
public record HospitalGuestRes(
        String name,
        String address,
        String imageUrl,
        Location location
) {
    public static HospitalGuestRes fromEntity(Hospital hospital){
        return HospitalGuestRes.builder()
                .name(hospital.getName())
                .address(hospital.getAddress())
                .imageUrl(hospital.getImageUrl())
                .location(hospital.getLocation())
                .build();
    }
}