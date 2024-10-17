package com.se.sos.domain.paramedic.dto;

import com.se.sos.domain.paramedic.entity.Paramedic;

import java.util.List;

public record ParamedicsRes (

        List<AmbulanceParaRes> paraResList
){
    public static ParamedicsRes from(List<Paramedic> paramedics) {
        List<AmbulanceParaRes> paramedicResList = paramedics.stream()
                                .map(AmbulanceParaRes::from).toList();

        return new ParamedicsRes(paramedicResList);
    }
}
