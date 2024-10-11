package com.se.sos.domain.patient.dto;

import com.se.sos.domain.patient.entity.Gender;
import com.se.sos.domain.patient.entity.Patient;

public record PatientRes (
    String name,
    int age,
    String phoneNumber,
    String medication,
    String reference,
    Gender gender
){
    public static PatientRes from(Patient patient){
        return new PatientRes(
                patient.getName(),
                patient.getAge(),
                patient.getPhoneNumber(),
                patient.getMedication(),
                patient.getReference(),
                patient.getGender()
        );
    }
}
