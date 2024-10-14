package com.se.sos.domain.paramedic.dto;

import com.se.sos.domain.ambulance.entity.Ambulance;
import com.se.sos.domain.paramedic.entity.Paramedic;
import jakarta.validation.constraints.NotBlank;

public record ParamedicReq(
        @NotBlank String name,
        @NotBlank String phoneNumber
) {
    public static Paramedic toEntity(ParamedicReq paramedicReq, Ambulance ambulance){
        return Paramedic.builder()
                .name(paramedicReq.name())
                .phoneNumber(paramedicReq.phoneNumber())
                .ambulance(ambulance)
                .build();
    }
}
