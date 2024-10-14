package com.se.sos.domain.admin.dto;

import com.se.sos.domain.ambulance.entity.Ambulance;
import com.se.sos.domain.hospital.entity.Hospital;

import java.util.UUID;

public record RegSummaryRes(
        UUID id,
        String name,
        String address,
        String telephoneNumber
) {
    public static RegSummaryRes fromHospital(Hospital hospital){
        return new RegSummaryRes(hospital.getId(), hospital.getName(), hospital.getAddress(), hospital.getTelephoneNumber());
    }

    public static RegSummaryRes fromAmbulance(Ambulance ambulance){
        return new RegSummaryRes(ambulance.getId(), ambulance.getName(), ambulance.getAddress(), ambulance.getTelephoneNumber());
    }


}
