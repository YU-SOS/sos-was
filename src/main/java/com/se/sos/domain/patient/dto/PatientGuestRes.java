package com.se.sos.domain.patient.dto;

import com.se.sos.domain.patient.entity.Patient;
import lombok.Builder;

@Builder
public record PatientGuestRes(
        String name,
        int age,
        String phoneNumber,
        String symptom
) {
    public static PatientGuestRes from(Patient patient) {
        return PatientGuestRes.builder()
                .name(patient.getName())
                .age(patient.getAge())
                .phoneNumber(patient.getPhoneNumber())
                .symptom(patient.getSymptom())
                .build();
    }
}
