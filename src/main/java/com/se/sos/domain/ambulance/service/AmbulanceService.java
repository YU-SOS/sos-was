package com.se.sos.domain.ambulance.service;

import com.se.sos.domain.ambulance.entity.Ambulance;
import com.se.sos.domain.ambulance.repository.AmbulanceRepository;
import com.se.sos.domain.paramedic.dto.ParamedicRegisterReq;
import com.se.sos.domain.paramedic.entity.Paramedic;
import com.se.sos.domain.paramedic.repository.ParamedicRepository;
import com.se.sos.global.exception.CustomException;
import com.se.sos.global.response.error.ErrorType;
import com.se.sos.global.response.success.SuccessRes;
import com.se.sos.global.response.success.SuccessType;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AmbulanceService {

    private final AmbulanceRepository ambulanceRepository;
    private final ParamedicRepository paramedicRepository;

    @Transactional
    public ResponseEntity<?> addParamedic(UUID id, ParamedicRegisterReq paramedicRegisterReq) {
        Ambulance ambulance = ambulanceRepository.findById(id)
                .orElseThrow(()-> new CustomException(ErrorType.AMBULANCE_NOT_FOUND));

        Paramedic paramedic = ParamedicRegisterReq.toEntity(paramedicRegisterReq, ambulance);

        ambulance.addParamedic(paramedic);

        paramedicRepository.save(paramedic);
        ambulanceRepository.save(ambulance);

        return ResponseEntity.status(SuccessType.PARAMEDIC_ADDED.getStatus())
                .body(SuccessRes.from(SuccessType.PARAMEDIC_ADDED));
    }



}
