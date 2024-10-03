package com.se.sos.domain.paramedic.dto;

import com.se.sos.domain.ambulance.entity.Ambulance;
import com.se.sos.domain.paramedic.entity.Paramedic;

public record ParamedicReq(
        String name,
        String phoneNumber
) {
    public static Paramedic toEntity(ParamedicReq paramedicReq, Ambulance ambulance){
        return Paramedic.builder()
                .name(paramedicReq.name())
                .phoneNumber(paramedicReq.phoneNumber())
                .ambulance(ambulance)
                .build();
    }
}
