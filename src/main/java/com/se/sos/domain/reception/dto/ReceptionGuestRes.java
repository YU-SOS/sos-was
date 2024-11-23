package com.se.sos.domain.reception.dto;

import com.se.sos.domain.ambulance.dto.AmbulanceGuestRes;
import com.se.sos.domain.ambulance.entity.Ambulance;
import com.se.sos.domain.hospital.dto.HospitalGuestRes;
import com.se.sos.domain.hospital.entity.Hospital;
import lombok.Builder;

@Builder
public record ReceptionGuestRes(
        String number,
        HospitalGuestRes hospital,
        AmbulanceGuestRes ambulance
) {
    public static ReceptionGuestRes of(String number, Hospital hospital, Ambulance ambulance) {
        return ReceptionGuestRes.builder()
                .number(number)
                .hospital(HospitalGuestRes.fromEntity(hospital))
                .ambulance(AmbulanceGuestRes.fromEntity(ambulance))
                .build();
    }
}
