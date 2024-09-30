package com.se.sos.domain.reception.response;

import com.se.sos.domain.ambulance.response.AmbulanceRes;
import com.se.sos.domain.hospital.response.HospitalRes;
import com.se.sos.domain.reception.entity.Reception;
import com.se.sos.domain.reception.entity.TransferStatus;

import java.time.LocalDateTime;
import java.util.UUID;

public record ReceptionRes(
        UUID id,
        TransferStatus status,
        LocalDateTime startTime,
        LocalDateTime endTime,
        AmbulanceRes ambulance,
        PatientRes patient,
        HospitalRes hospital
) {
    public static ReceptionRes from(Reception reception) {
        return new ReceptionRes(
                reception.getId(),
                reception.getStatus(),
                reception.getStartTime(),
                reception.getEndTime(),
                AmbulanceRes.from(reception.getAmbulance()),
                PatientRes.from(reception.getPatient()),
                HospitalRes.from(reception.getHospital())
        );
    }

}
