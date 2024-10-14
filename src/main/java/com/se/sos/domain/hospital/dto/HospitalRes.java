package com.se.sos.domain.hospital.dto;


import com.se.sos.domain.ambulance.entity.Location;
import com.se.sos.domain.category.dto.CategoryRes;
import com.se.sos.domain.hospital.entity.EmergencyRoomStatus;
import com.se.sos.domain.hospital.entity.Hospital;
import com.se.sos.domain.user.entity.Role;

import java.util.List;
import java.util.UUID;

public record HospitalRes (
        UUID id,
        String hospitalId,
        String name,
        String address,
        String telephoneNumber,
        String imageUrl,
        Location location,
        Role role,
        List<CategoryRes> categories,
        EmergencyRoomStatus emergencyRoomStatus
)
{
    public static HospitalRes from(Hospital hospital) {
        List<CategoryRes> categoryResList = hospital.getCategoryHospitals().stream()
                .map(categoryHospital -> CategoryRes.from(categoryHospital.getCategory()))
                .toList();

        return new HospitalRes(
                hospital.getId(),
                hospital.getHospitalId(),
                hospital.getName(),
                hospital.getAddress(),
                hospital.getTelephoneNumber(),
                hospital.getImageUrl(),
                hospital.getLocation(),
                hospital.getRole(),
                categoryResList,
                hospital.getEmergencyRoomStatus()
        );
    }
}

