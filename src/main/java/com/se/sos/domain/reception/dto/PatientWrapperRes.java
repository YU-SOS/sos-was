package com.se.sos.domain.reception.dto;

import com.se.sos.domain.reception.entity.ReceptionStatus;
import com.se.sos.domain.reception.response.PatientRes;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class PatientWrapperRes {
    private ReceptionStatus status;
    private PatientRes patient;

    public PatientWrapperRes(ReceptionStatus status, PatientRes patientRes) {
        this.status = status;
        this.patient = patientRes;
    }
}

