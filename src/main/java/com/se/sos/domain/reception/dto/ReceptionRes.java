package com.se.sos.domain.reception.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.se.sos.domain.ambulance.dto.AmbulanceRes;
import com.se.sos.domain.comment.dto.CommentRes;
import com.se.sos.domain.hospital.dto.HospitalRes;
import com.se.sos.domain.paramedic.dto.ParamedicRes;
import com.se.sos.domain.patient.dto.PatientReq;
import com.se.sos.domain.reception.entity.Reception;
import com.se.sos.domain.reception.entity.ReceptionStatus;
import com.se.sos.domain.patient.dto.PatientRes;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Builder
public record ReceptionRes(
        UUID id,
        String number,
        LocalDateTime startTime,
        LocalDateTime endTime,
        AmbulanceRes ambulance,
        HospitalRes hospital,
        PatientRes patient,
        List<CommentRes> comments,
        ReceptionStatus receptionStatus,
        @JsonProperty(value = "paramedic")
        ParamedicRes paramedicRes
) {
    public static ReceptionRes from(Reception reception) {

        List<CommentRes> commentResList = reception.getCommentList().stream()
                .map(CommentRes::from)
                .toList();

        return ReceptionRes.builder()
                .id(reception.getId())
                .number(reception.getNumber())
                .startTime(reception.getStartTime())
                .endTime(reception.getEndTime())
                .ambulance(AmbulanceRes.from(reception.getAmbulance()))
                .hospital(HospitalRes.from(reception.getHospital()))
                .patient(PatientRes.from(reception.getPatient()))
                .comments(commentResList)
                .receptionStatus(reception.getReceptionStatus())
                .paramedicRes(ParamedicRes.fromEntity(reception.getParamedic()))
                .build();
    }
}
