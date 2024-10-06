package com.se.sos.domain.reception.dto;


import com.se.sos.domain.ambulance.entity.Ambulance;
import com.se.sos.domain.comment.dto.CommentRes;
import com.se.sos.domain.hospital.entity.Hospital;
import com.se.sos.domain.reception.entity.Reception;
import com.se.sos.domain.reception.entity.Patient;
import com.se.sos.domain.reception.entity.TransferStatus;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record ReceptionRes(
        UUID id,
        LocalDateTime startTime,
        LocalDateTime endTime,
        TransferStatus status,
        Ambulance ambulance,
        Hospital hospital,
        Patient patient,
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
                reception.getAmbulance(),
                reception.getHospital(),
                reception.getPatient(),
                commentResList
        );
    }
}
