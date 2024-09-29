package com.se.sos.domain.admin.service;

import com.se.sos.domain.admin.dto.RegSummaryRes;
import com.se.sos.domain.ambulance.entity.Ambulance;
import com.se.sos.domain.ambulance.repository.AmbulanceRepository;
import com.se.sos.domain.hospital.entity.Hospital;
import com.se.sos.domain.hospital.repository.HospitalRepository;
import com.se.sos.domain.user.entity.Role;
import com.se.sos.global.exception.CustomException;
import com.se.sos.global.response.error.ErrorType;
import com.se.sos.global.response.success.SuccessRes;
import com.se.sos.global.response.success.SuccessType;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final AmbulanceRepository ambulanceRepository;;
    private final HospitalRepository hospitalRepository;

    public ResponseEntity<?> getAllRegistration(){

        List<RegSummaryRes> ambulanceList = ambulanceRepository.findByRole(Role.AMB_GUEST)
                .stream()
                .map(amb -> RegSummaryRes.fromAmbulance(amb))
                .toList();

        List<RegSummaryRes> hospitalList = hospitalRepository.findByRole(Role.HOS_GUEST)
                .stream()
                .map(hos -> RegSummaryRes.fromHospital(hos))
                .toList();

        Map<String, Object> map = new HashMap<>();

        map.put("ambulanceList", ambulanceList);
        map.put("hospitalList", hospitalList);

        return ResponseEntity.status(SuccessType.OK.getStatus())
                .body(SuccessRes.from(map));
    }

    public ResponseEntity<?> getRegistration(Role role, UUID id){

        if(role.equals(Role.AMB_GUEST)){
            Ambulance ambulance = ambulanceRepository.findById(id)
                    .orElseThrow(() -> new CustomException(ErrorType.AMBULANCE_NOT_FOUND));

            return ResponseEntity.status(SuccessType.OK.getStatus())
                    .body(SuccessRes.from(ambulance));

        } else if(role.equals(Role.HOS_GUEST)){
            Hospital hospital = hospitalRepository.findById(id)
                    .orElseThrow(() -> new CustomException(ErrorType.HOSPITAL_NOT_FOUND));

            return ResponseEntity.status(SuccessType.OK.getStatus())
                    .body(SuccessRes.from(hospital));
        } else {
            throw new CustomException(ErrorType.BAD_REQUEST);
        }
    }
}
