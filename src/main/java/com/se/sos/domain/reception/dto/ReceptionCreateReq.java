package com.se.sos.domain.reception.dto;

import com.se.sos.domain.ambulance.entity.Ambulance;
import com.se.sos.domain.hospital.entity.Hospital;
import com.se.sos.domain.paramedic.entity.Paramedic;
import com.se.sos.domain.patient.entity.Patient;
import com.se.sos.domain.reception.entity.Reception;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@NoArgsConstructor
public class ReceptionCreateReq {

    Patient patient;
    String hospitalName;
    LocalDateTime startTime;
    UUID paramedicId;

    public static Reception toEntity(ReceptionCreateReq receptionCreateReq, Ambulance ambulance , Hospital hospital, Paramedic paramedic) {
        return Reception.builder()
                .startTime(receptionCreateReq.getStartTime())
                .patient(receptionCreateReq.getPatient())
                .ambulance(ambulance)
                .hospital(hospital)
                .paramedic(paramedic)
                .build();
    }
}
