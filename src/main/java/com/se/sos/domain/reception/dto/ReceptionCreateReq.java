package com.se.sos.domain.reception.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.se.sos.domain.ambulance.entity.Ambulance;
import com.se.sos.domain.hospital.entity.Hospital;
import com.se.sos.domain.paramedic.entity.Paramedic;
import com.se.sos.domain.patient.dto.PatientReq;
import com.se.sos.domain.patient.entity.Patient;
import com.se.sos.domain.reception.entity.Reception;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@NoArgsConstructor
public class ReceptionCreateReq {

    @JsonProperty("patient")
    PatientReq patientReq;
    @NotBlank(message = "병원 이름은 필수 입력값 입니다.")
    String hospitalName;
    LocalDateTime startTime;
    @NotNull(message = "선탑 구급대원은 필수 전달값 입니다.")
    UUID paramedicId;

    public static Reception toEntity(ReceptionCreateReq receptionCreateReq, Ambulance ambulance , Hospital hospital, Paramedic paramedic, String number) {
        return Reception.builder()
                .startTime(receptionCreateReq.getStartTime())
                .patient(PatientReq.toEntity(receptionCreateReq.getPatientReq()))
                .ambulance(ambulance)
                .hospital(hospital)
                .paramedic(paramedic)
                .number(number)
                .build();
    }
}
