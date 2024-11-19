package com.se.sos.domain.ambulance.service;

import com.se.sos.domain.ambulance.dto.AmbulanceRes;
import com.se.sos.domain.ambulance.entity.Ambulance;
import com.se.sos.domain.ambulance.repository.AmbulanceRepository;
import com.se.sos.domain.paramedic.dto.ParamedicReq;
import com.se.sos.domain.paramedic.dto.ParamedicRes;
import com.se.sos.domain.paramedic.entity.Paramedic;
import com.se.sos.domain.paramedic.repository.ParamedicRepository;
import com.se.sos.global.exception.CustomException;
import com.se.sos.global.response.error.ErrorType;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AmbulanceService {

    private final AmbulanceRepository ambulanceRepository;
    private final ParamedicRepository paramedicRepository;


    @Transactional(readOnly = true)
    public AmbulanceRes getAmbulanceById(UUID id){
        Ambulance ambulance = ambulanceRepository.findById(id)
                .orElseThrow(()-> new CustomException(ErrorType.AMBULANCE_NOT_FOUND));

        return AmbulanceRes.from(ambulance);
    }

    @Transactional(readOnly = true)
    public List<ParamedicRes> getAllParamedicByAmbulanceId(UUID id){
        Ambulance ambulance = ambulanceRepository.findById(id)
                .orElseThrow(()-> new CustomException(ErrorType.AMBULANCE_NOT_FOUND));

        return ambulance.getParamedics().stream()
                .map(ParamedicRes::fromEntity)
                .toList();

    }

    @Transactional
    public void addParamedic(UUID id, ParamedicReq paramedicReq) {
        Ambulance ambulance = ambulanceRepository.findById(id)
                .orElseThrow(()-> new CustomException(ErrorType.AMBULANCE_NOT_FOUND));

        Paramedic paramedic = ParamedicReq.toEntity(paramedicReq, ambulance);

        ambulance.addParamedic(paramedic);

        paramedicRepository.save(paramedic);
        ambulanceRepository.save(ambulance);
    }

    @Transactional
    public void updateParamedic(UUID ambulanceId, UUID memberId, ParamedicReq paramedicReq) {

        Paramedic paramedic = paramedicRepository.findByIdAndAmbulanceId(memberId, ambulanceId)
                .orElseThrow(()-> new CustomException(ErrorType.PARAMEDIC_NOT_FOUND));

        paramedic.updateInfo(paramedicReq);
        paramedicRepository.save(paramedic);
    }

    @Transactional
    public void deleteParamedic(UUID ambulanceId, UUID memberId) {
        paramedicRepository.deleteByIdAndAmbulanceId(memberId, ambulanceId);
    }



}
