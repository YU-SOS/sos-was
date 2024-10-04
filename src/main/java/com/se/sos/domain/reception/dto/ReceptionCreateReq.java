package com.se.sos.domain.reception.dto;

import com.se.sos.domain.ambulance.entity.Ambulance;
import com.se.sos.domain.hospital.entity.Hospital;
import com.se.sos.domain.reception.entity.Patient;
import com.se.sos.domain.reception.entity.Reception;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class ReceptionCreateReq {

    Patient patient;
    String hospitalName;
    LocalDateTime startTime;

    public static Reception toEntity(ReceptionCreateReq receptionCreateReq, Ambulance ambulance , Hospital hospital) {
        return Reception.builder()
                .startTime(receptionCreateReq.getStartTime())
                .patient(receptionCreateReq.getPatient())
                .ambulance(ambulance)
                .hospital(hospital)
                .build();
    }
}
