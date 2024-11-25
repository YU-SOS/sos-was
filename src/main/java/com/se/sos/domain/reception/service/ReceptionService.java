package com.se.sos.domain.reception.service;

import com.se.sos.domain.ambulance.entity.Ambulance;
import com.se.sos.domain.ambulance.repository.AmbulanceRepository;
import com.se.sos.domain.comment.dto.CommentReq;
import com.se.sos.domain.comment.entity.Comment;
import com.se.sos.domain.comment.repository.CommentRepository;
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

import java.security.SecureRandom;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReceptionService {
    private final ReceptionRepository receptionRepository;
    private final AmbulanceRepository ambulanceRepository;
    private final HospitalRepository hospitalRepository;
    private final PatientRepository patientRepository;
    private final ParamedicRepository paramedicRepository;
    private final CommentRepository commentRepository;

    @Transactional
    public UUID createReception(ReceptionCreateReq req, UUID ambulanceId) {

        String hospitalName = req.getHospitalName();
        UUID paramedicId = req.getParamedicId();

        Ambulance ambulance = ambulanceRepository.findById(ambulanceId)
                .orElseThrow(() -> new CustomException(ErrorType.AMBULANCE_NOT_FOUND));
        Hospital hospital = hospitalRepository.findByName(hospitalName)
                .orElseThrow(() -> new CustomException(ErrorType.HOSPITAL_NOT_FOUND));
        Paramedic paramedic = paramedicRepository.findById(paramedicId)
                .orElseThrow(() -> new CustomException(ErrorType.PARAMEDIC_NOT_FOUND));

        String number;
        do {
            number = generateReceptionNumber();
        } while(receptionRepository.existsByNumber(number));

        Reception reception = ReceptionCreateReq.toEntity(req, ambulance, hospital, paramedic, number);
        patientRepository.save(reception.getPatient());
        receptionRepository.save(reception);

        return reception.getId();
    }

    private String generateReceptionNumber(){
        final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        final int LENGTH = 6;
        SecureRandom random = new SecureRandom();
        StringBuilder sb = new StringBuilder();

        for(int i = 0 ; i < LENGTH ; i++){
            int index = random.nextInt(CHARACTERS.length());
            sb.append(CHARACTERS.charAt(index));
        }

        return sb.toString();
    }

    public ReceptionRes findReceptionById(UUID id) {
        Reception reception = receptionRepository.findReceptionById(id)
                .orElseThrow(() -> new CustomException(ErrorType.RECEPTION_NOT_FOUND));

        return ReceptionRes.from(reception);
    }

    public ReceptionRes findReceptionByNumber(String number){
        Reception reception = receptionRepository.findByNumber(number)
                .orElseThrow(() -> new CustomException(ErrorType.RECEPTION_NOT_FOUND));

        return ReceptionRes.from(reception);
    }

    @Transactional
    public void acceptReception(UUID receptionId, boolean isApproved){

        Reception reception = receptionRepository.findById(receptionId)
                .orElseThrow(() -> new CustomException(ErrorType.RECEPTION_NOT_FOUND));

        if(isApproved)
            reception.updateReceptionStatus(ReceptionStatus.MOVE);
        else{
            reception.updateReceptionStatus(ReceptionStatus.REJECTED);
            reception.deleteHospital();
        }
    }

    @Transactional
    public UUID reRequestReception(UUID receptionId, UUID hospitalId){
        Hospital hospital = hospitalRepository.findById(hospitalId)
                .orElseThrow(() -> new CustomException(ErrorType.HOSPITAL_NOT_FOUND));

        Reception reception = receptionRepository.findById(receptionId)
                .orElseThrow(() -> new CustomException(ErrorType.RECEPTION_NOT_FOUND));

        reception.updateHospital(hospital);

        return reception.getId();
    }

    @Transactional
    public void closeReception(UUID receptionId){
        Reception reception = receptionRepository.findById(receptionId)
                .orElseThrow(() -> new CustomException(ErrorType.RECEPTION_NOT_FOUND));

        reception.closeReception();

        receptionRepository.save(reception);
    }

    public ReceptionGuestRes findReceptionForGuest(String number){
        Reception reception = receptionRepository.findByNumber(number)
                .orElseThrow(() -> new CustomException(ErrorType.RECEPTION_NOT_FOUND));

        return ReceptionGuestRes.of(reception.getNumber(), reception.getHospital(), reception.getAmbulance());
    }

    @Transactional
    public void addComment(UUID receptionId, CommentReq req){
        Reception reception = receptionRepository.findById(receptionId)
                .orElseThrow(() -> new CustomException(ErrorType.RECEPTION_NOT_FOUND));

        Comment comment = Comment.builder()
                .description(req.description())
                .reception(reception)
                .build();

        commentRepository.save(comment);
    }
}
