package com.se.sos.domain.paramedic.dto;

import com.se.sos.domain.paramedic.entity.Paramedic;
import lombok.Builder;

import java.util.UUID;

@Builder
public record ParamedicRes(
        UUID id,
        String name,
        String phoneNumber
) {
    public static ParamedicRes fromEntity(Paramedic paramedic) {
        return ParamedicRes.builder()
                .id(paramedic.getId())
                .name(paramedic.getName())
                .phoneNumber(paramedic.getPhoneNumber())
                .build();
    }
}
