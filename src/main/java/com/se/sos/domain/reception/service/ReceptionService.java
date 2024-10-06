package com.se.sos.domain.reception.service;

import com.se.sos.domain.ambulance.entity.Ambulance;
import com.se.sos.domain.ambulance.repository.AmbulanceRepository;
import com.se.sos.domain.hospital.entity.Hospital;
import com.se.sos.domain.hospital.repository.HospitalRepository;
import com.se.sos.domain.reception.dto.ReceptionCreateReq;
import com.se.sos.domain.reception.dto.ReceptionRes;
import com.se.sos.domain.reception.entity.Reception;
import com.se.sos.domain.reception.repository.PatientRepository;
import com.se.sos.domain.reception.repository.ReceptionRepository;
import com.se.sos.global.exception.CustomException;
import com.se.sos.global.response.error.ErrorType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReceptionService {
    private final ReceptionRepository receptionRepository;
    private final AmbulanceRepository ambulanceRepository;
    private final HospitalRepository hospitalRepository;
    private final PatientRepository patientRepository;

    public void createReception(ReceptionCreateReq receptionCreateReq, UUID ambulanceId) {
        Ambulance ambulance = ambulanceRepository.findById(ambulanceId)
                .orElseThrow(() -> new CustomException(ErrorType.AMBULANCE_NOT_FOUND));
        Hospital hospital = hospitalRepository.findByName(receptionCreateReq.getHospitalName())
                .orElseThrow(() -> new CustomException(ErrorType.HOSPITAL_NOT_FOUND));

        Reception reception = ReceptionCreateReq.toEntity(receptionCreateReq, ambulance, hospital);
        patientRepository.save(reception.getPatient());
        receptionRepository.save(reception);
    }

    public ReceptionRes findReceptionById(String id) {
        UUID receptionId = UUID.fromString(id);
        Reception reception = receptionRepository.findReceptionById(receptionId)
                .orElseThrow(() -> new CustomException(ErrorType.RECEPTION_NOT_FOUND));

        return ReceptionRes.from(reception);
    }
}
