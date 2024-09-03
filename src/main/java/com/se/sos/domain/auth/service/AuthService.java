package com.se.sos.domain.auth.service;

import com.se.sos.domain.ambulance.entity.Ambulance;
import com.se.sos.domain.ambulance.repository.AmbulanceRepository;
import com.se.sos.domain.auth.dto.AmbulanceSignupReq;
import com.se.sos.domain.auth.dto.HospitalSignupReq;
import com.se.sos.domain.auth.dto.SignupReq;
import com.se.sos.domain.hospital.entity.Hospital;
import com.se.sos.domain.hospital.repository.HospitalRepository;
import com.se.sos.global.exception.CustomException;
import com.se.sos.global.response.error.ErrorType;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AmbulanceRepository ambulanceRepository;;
    private final HospitalRepository hospitalRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public void signupForAmbulance(AmbulanceSignupReq ambulanceSignupReq){
        if(ambulanceRepository.existsByName(ambulanceSignupReq.getName()))
            throw new CustomException(ErrorType.ALREADY_EXISTS_AMBULANCE);

        Ambulance newAmbulance = AmbulanceSignupReq.toEntity(ambulanceSignupReq, bCryptPasswordEncoder.encode(ambulanceSignupReq.getPassword()));
        ambulanceRepository.save(newAmbulance);
    }

    public void signupForHospital(HospitalSignupReq hospitalSignupReq){
        if(hospitalRepository.existsByHospitalId(hospitalSignupReq.getName()))
            throw new CustomException(ErrorType.ALREADY_EXISTS_HOSPITAL);

        Hospital newHospital = HospitalSignupReq.toEntity(hospitalSignupReq, bCryptPasswordEncoder.encode(hospitalSignupReq.getPassword()));
        hospitalRepository.save(newHospital);

    }
}
