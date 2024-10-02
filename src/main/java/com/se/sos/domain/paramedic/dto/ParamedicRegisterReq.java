package com.se.sos.domain.paramedic.dto;

import com.se.sos.domain.ambulance.entity.Ambulance;
import com.se.sos.domain.paramedic.entity.Paramedic;

public record ParamedicRegisterReq(
        String name,
        String phoneNumber
) {
    public static Paramedic toEntity(ParamedicRegisterReq paramedicRegisterReq, Ambulance ambulance){
        return Paramedic.builder()
                .name(paramedicRegisterReq.name())
                .phoneNumber(paramedicRegisterReq.phoneNumber())
                .ambulance(ambulance)
                .build();
    }
}
