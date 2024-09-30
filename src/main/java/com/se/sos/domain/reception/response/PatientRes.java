package com.se.sos.domain.reception.response;

import com.se.sos.domain.reception.entity.Gender;
import com.se.sos.domain.reception.entity.Patient;
import com.se.sos.domain.reception.entity.TransferStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record PatientRes (
    UUID id,
    String name,
    int age,
    String phoneNumber,
    String medication,
    String reference,
    Gender gender,
    TransferStatus status
){
    public static PatientRes from(Patient patient){
        return new PatientRes(
                patient.getId(),
                patient.getName(),
                patient.getAge(),
                patient.getPhoneNumber(),
                patient.getMedication(),
                patient.getReference(),
                patient.getGender(),
                patient.getStatus()
        );
    }
}
