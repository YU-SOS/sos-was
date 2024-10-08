package com.se.sos.domain.reception.dto;

import com.se.sos.domain.reception.entity.ReceptionStatus;
import lombok.Getter;

@Getter
public class VisitReq {
    private ReceptionStatus receptionStatus;
}
