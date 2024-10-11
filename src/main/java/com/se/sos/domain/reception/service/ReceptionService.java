package com.se.sos.domain.reception.service;

import com.se.sos.domain.ambulance.entity.Ambulance;
import com.se.sos.domain.ambulance.repository.AmbulanceRepository;
import com.se.sos.domain.hospital.entity.Hospital;
import com.se.sos.domain.hospital.repository.HospitalRepository;
import com.se.sos.domain.paramedic.entity.Paramedic;
import com.se.sos.domain.paramedic.repository.ParamedicRepository;
import com.se.sos.domain.patient.repository.PatientRepository;
import com.se.sos.domain.reception.dto.ReceptionCreateReq;
import com.se.sos.domain.reception.dto.ReceptionGuestRes;
import com.se.sos.domain.reception.dto.ReceptionRes;
import com.se.sos.domain.reception.entity.Reception;
import com.se.sos.domain.reception.entity.ReceptionStatus;
import com.se.sos.domain.reception.repository.ReceptionRepository;
import com.se.sos.global.exception.CustomException;
import com.se.sos.global.response.error.ErrorType;
import jakarta.transaction.Transactional;
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
    private final ParamedicRepository paramedicRepository;

    @Transactional
    public void createReception(ReceptionCreateReq req, UUID ambulanceId) {

        String hospitalName = req.getHospitalName();
        UUID paramedicId = req.getParamedicId();

        Ambulance ambulance = ambulanceRepository.findById(ambulanceId)
                .orElseThrow(() -> new CustomException(ErrorType.AMBULANCE_NOT_FOUND));
        Hospital hospital = hospitalRepository.findByName(hospitalName)
                .orElseThrow(() -> new CustomException(ErrorType.HOSPITAL_NOT_FOUND));
        Paramedic paramedic = paramedicRepository.findById(paramedicId)
                .orElseThrow(() -> new CustomException(ErrorType.PARAMEDIC_NOT_FOUND));

        Reception reception = ReceptionCreateReq.toEntity(req, ambulance, hospital, paramedic);
        patientRepository.save(reception.getPatient());
        receptionRepository.save(reception);
    }


    public ReceptionRes findReceptionById(UUID id) {
        Reception reception = receptionRepository.findReceptionById(id)
                .orElseThrow(() -> new CustomException(ErrorType.RECEPTION_NOT_FOUND));

        return ReceptionRes.from(reception);
    }


    @Transactional
    public void acceptReception(UUID receptionId, boolean isApproved){

        Reception reception = receptionRepository.findById(receptionId)
                .orElseThrow(() -> new CustomException(ErrorType.RECEPTION_NOT_FOUND));

        if(isApproved)
            reception.updateReceptionStatus(ReceptionStatus.MOVE);
        else
            reception.updateReceptionStatus(ReceptionStatus.REJECTED); // 되면서 병원 아예 reception에서 삭제?

        receptionRepository.save(reception);
    }

    @Transactional
    public void reRequestReception(UUID receptionId, UUID hospitalId){
        Hospital hospital = hospitalRepository.findById(hospitalId)
                .orElseThrow(() -> new CustomException(ErrorType.HOSPITAL_NOT_FOUND));

        Reception reception = receptionRepository.findById(receptionId)
                .orElseThrow(() -> new CustomException(ErrorType.RECEPTION_NOT_FOUND));

        reception.updateHospital(hospital);

        receptionRepository.save(reception);
    }

    @Transactional
    public void closeReception(UUID receptionId){
        Reception reception = receptionRepository.findById(receptionId)
                .orElseThrow(() -> new CustomException(ErrorType.RECEPTION_NOT_FOUND));

        reception.closeReception();

        receptionRepository.save(reception);
    }

    public ReceptionGuestRes findReceptionForGuest(UUID id){
        Reception reception = receptionRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorType.RECEPTION_NOT_FOUND));

        return ReceptionGuestRes.of(reception.getHospital(), reception.getAmbulance());
    }
}
