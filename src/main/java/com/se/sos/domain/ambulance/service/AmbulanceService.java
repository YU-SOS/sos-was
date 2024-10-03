package com.se.sos.domain.ambulance.service;

import com.se.sos.domain.ambulance.entity.Ambulance;
import com.se.sos.domain.ambulance.repository.AmbulanceRepository;
import com.se.sos.domain.paramedic.dto.ParamedicReq;
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
    public ResponseEntity<?> addParamedic(UUID id, ParamedicReq paramedicReq) {
        Ambulance ambulance = ambulanceRepository.findById(id)
                .orElseThrow(()-> new CustomException(ErrorType.AMBULANCE_NOT_FOUND));

        Paramedic paramedic = ParamedicReq.toEntity(paramedicReq, ambulance);

        ambulance.addParamedic(paramedic);

        paramedicRepository.save(paramedic);
        ambulanceRepository.save(ambulance);

        return ResponseEntity.status(SuccessType.PARAMEDIC_ADDED.getStatus())
                .body(SuccessRes.from(SuccessType.PARAMEDIC_ADDED));
    }

    @Transactional
    public ResponseEntity<?> updateParamedic(UUID ambulanceId, UUID memberId, ParamedicReq paramedicReq) {

        Paramedic paramedic = paramedicRepository.findByIdAndAmbulanceId(memberId, ambulanceId)
                .orElseThrow(()-> new CustomException(ErrorType.PARAMEDIC_NOT_FOUND));

        paramedic.updateInfo(paramedicReq);
        paramedicRepository.save(paramedic);

        return ResponseEntity.status(SuccessType.OK.getStatus())
                .body(SuccessRes.from(SuccessType.OK));
    }

    @Transactional
    public ResponseEntity<?> deleteParamedic(UUID ambulanceId, UUID memberId) {
        paramedicRepository.deleteByIdAndAmbulanceId(memberId, ambulanceId);

        return ResponseEntity.status(SuccessType.OK.getStatus())
                .body(SuccessRes.from(SuccessType.OK));
    }


}
