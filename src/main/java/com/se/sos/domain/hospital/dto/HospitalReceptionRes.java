package com.se.sos.domain.hospital.dto;

import com.se.sos.domain.ambulance.dto.AmbulanceRes;
import com.se.sos.domain.paramedic.dto.ParamedicRes;
import com.se.sos.domain.reception.entity.Reception;
import com.se.sos.domain.reception.entity.ReceptionStatus;
import com.se.sos.domain.patient.dto.PatientRes;

import java.time.LocalDateTime;
import java.util.UUID;

public record HospitalReceptionRes(
        UUID id,
        LocalDateTime startTime,
        AmbulanceRes ambulance,
        PatientRes patient,
        ReceptionStatus receptionStatus,
        ParamedicRes paramedicRes
) {
    public static HospitalReceptionRes from(Reception reception) {
        return new HospitalReceptionRes(
                reception.getId(),
                reception.getStartTime(),
                AmbulanceRes.from(reception.getAmbulance()),
                PatientRes.from(reception.getPatient()),
                reception.getReceptionStatus(),
                ParamedicRes.fromEntity(reception.getParamedic())
        );
    }
}

