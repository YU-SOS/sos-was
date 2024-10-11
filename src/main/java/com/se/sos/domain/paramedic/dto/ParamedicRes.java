package com.se.sos.domain.paramedic.dto;

import com.se.sos.domain.paramedic.entity.Paramedic;
import lombok.Builder;

@Builder
public record ParamedicRes(
        String name,
        String phoneNumber
) {
    public static ParamedicRes fromEntity(Paramedic paramedic) {
        return ParamedicRes.builder()
                .name(paramedic.getName())
                .phoneNumber(paramedic.getPhoneNumber())
                .build();
    }
}
