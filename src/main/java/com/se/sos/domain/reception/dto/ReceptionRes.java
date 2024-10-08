package com.se.sos.domain.reception.dto;


import com.se.sos.domain.ambulance.dto.AmbulanceRes;
import com.se.sos.domain.comment.dto.CommentRes;
import com.se.sos.domain.hospital.dto.HospitalRes;
import com.se.sos.domain.reception.entity.Reception;
import com.se.sos.domain.reception.entity.TransferStatus;
import com.se.sos.domain.reception.response.PatientRes;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record ReceptionRes(
        UUID id,
        LocalDateTime startTime,
        LocalDateTime endTime,
        TransferStatus status,
        AmbulanceRes ambulance,
        HospitalRes hospital,
        PatientRes patient,
        List<CommentRes> comments
) {
    public static ReceptionRes from(Reception reception) {

        List<CommentRes> commentResList = reception.getCommentList().stream()
                .map(CommentRes::from)
                .toList();

        return new ReceptionRes(
                reception.getId(),
                reception.getStartTime(),
                reception.getEndTime(),
                reception.getStatus(),
                AmbulanceRes.from(reception.getAmbulance()),
                HospitalRes.from(reception.getHospital()),
                PatientRes.from(reception.getPatient()),
                commentResList
        );
    }
}
