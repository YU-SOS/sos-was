package com.se.sos.domain.patient.dto;

import com.se.sos.domain.patient.entity.Gender;
import com.se.sos.domain.patient.entity.Patient;
import com.se.sos.domain.patient.entity.Severity;
import lombok.Builder;

@Builder
public record PatientRes (
    String name,
    int age,
    String phoneNumber,
    String symptom,
    String medication,
    String reference,
    Gender gender,
    Severity severity

){
    public static PatientRes from(Patient patient){
        return PatientRes.builder()
                .name(patient.getName())
                .age(patient.getAge())
                .phoneNumber(patient.getPhoneNumber())
                .symptom(patient.getSymptom())
                .medication(patient.getMedication())
                .reference(patient.getReference())
                .gender(patient.getGender())
                .severity(patient.getSeverity())
                .build();
    }
}
