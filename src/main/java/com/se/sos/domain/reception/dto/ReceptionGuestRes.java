package com.se.sos.domain.reception.dto;

import com.se.sos.domain.ambulance.dto.AmbulanceGuestRes;
import com.se.sos.domain.ambulance.entity.Ambulance;
import com.se.sos.domain.hospital.dto.HospitalGuestRes;
import com.se.sos.domain.hospital.entity.Hospital;
import com.se.sos.domain.patient.dto.PatientGuestRes;
import com.se.sos.domain.patient.entity.Patient;
import lombok.Builder;

@Builder
public record ReceptionGuestRes(
        String number,
        HospitalGuestRes hospital,
        AmbulanceGuestRes ambulance,
        PatientGuestRes patient
) {
    public static ReceptionGuestRes of(String number, Hospital hospital, Ambulance ambulance, Patient patient) {
        return ReceptionGuestRes.builder()
                .number(number)
                .hospital(HospitalGuestRes.fromEntity(hospital))
                .ambulance(AmbulanceGuestRes.fromEntity(ambulance))
                .patient(PatientGuestRes.from(patient))
                .build();
    }
}
