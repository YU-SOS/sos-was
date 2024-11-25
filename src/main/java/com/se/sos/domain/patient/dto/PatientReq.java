package com.se.sos.domain.patient.dto;

import com.se.sos.domain.patient.entity.Gender;
import com.se.sos.domain.patient.entity.Patient;
import com.se.sos.domain.patient.entity.Severity;
import jakarta.validation.constraints.NotBlank;

public record PatientReq(
        String name,
        int age,
        String phoneNumber,
        @NotBlank(message = "증상은 필수 입력값입니다.")
        String symptom,
        String medication,
        String reference,
        Gender gender,
        Severity severity
) {
    public static Patient toEntity(PatientReq patientReq){
        return Patient.builder()
                .name(patientReq.name())
                .age(patientReq.age())
                .phoneNumber(patientReq.phoneNumber())
                .symptom(patientReq.symptom())
                .medication(patientReq.medication())
                .reference(patientReq.reference())
                .gender(patientReq.gender())
                .severity(patientReq.severity())
                .build();
    }
}
