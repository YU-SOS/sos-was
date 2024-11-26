package com.se.sos.domain.hospital.dto;

import com.se.sos.domain.ambulance.dto.AmbulanceRes;
import com.se.sos.domain.paramedic.dto.ParamedicRes;
import com.se.sos.domain.patient.dto.PatientReq;
import com.se.sos.domain.reception.entity.Reception;
import com.se.sos.domain.reception.entity.ReceptionStatus;
import com.se.sos.domain.patient.dto.PatientRes;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
public record HospitalReceptionRes(
        UUID id,
        String number,
        LocalDateTime startTime,
        AmbulanceRes ambulance,
        PatientRes patient,
        ReceptionStatus receptionStatus,
        ParamedicRes paramedicRes
) {
    public static HospitalReceptionRes from(Reception reception) {
        return HospitalReceptionRes.builder()
                .id(reception.getId())
                .number(reception.getNumber())
                .startTime(reception.getStartTime())
                .ambulance(AmbulanceRes.from(reception.getAmbulance()))
                .patient(PatientRes.from(reception.getPatient()))
                .receptionStatus(reception.getReceptionStatus())
                .paramedicRes(ParamedicRes.fromEntity(reception.getParamedic()))
                .build();
    }
}

