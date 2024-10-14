package com.se.sos.domain.hospital.dto;

import com.se.sos.domain.ambulance.entity.Location;
import com.se.sos.domain.hospital.entity.Hospital;
import lombok.Builder;

import java.util.UUID;

@Builder
public record HospitalRegRes(
        UUID id,
        String name,
        String address,
        String telephoneNumber,
        String hospitalId,
        String password,
        Location location,
        String imageUrl
) {
    public static HospitalRegRes fromEntity(Hospital hospital){
        return HospitalRegRes.builder()
                .id(hospital.getId())
                .name(hospital.getName())
                .address(hospital.getAddress())
                .telephoneNumber(hospital.getTelephoneNumber())
                .hospitalId((hospital.getHospitalId()))
                .password(hospital.getPassword())
                .location(hospital.getLocation())
                .imageUrl(hospital.getImageUrl())
                .build();
    }
}
