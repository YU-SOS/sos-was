package com.se.sos.domain.paramedic.dto;

import com.se.sos.domain.paramedic.entity.Paramedic;
import lombok.Builder;

import java.util.UUID;

@Builder
public record AmbulanceParaRes(
        UUID id,
        String name,
        String phoneNumber
) {
    public static AmbulanceParaRes from(Paramedic paramedic) {
        return AmbulanceParaRes.builder()
                .id(paramedic.getId())
                .name(paramedic.getName())
                .phoneNumber(paramedic.getPhoneNumber())
                .build();
    }
}
