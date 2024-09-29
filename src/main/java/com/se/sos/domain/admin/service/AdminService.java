package com.se.sos.domain.admin.service;

import com.se.sos.domain.admin.dto.RegSummaryRes;
import com.se.sos.domain.ambulance.repository.AmbulanceRepository;
import com.se.sos.domain.hospital.repository.HospitalRepository;
import com.se.sos.domain.user.entity.Role;
import com.se.sos.global.response.success.SuccessRes;
import com.se.sos.global.response.success.SuccessType;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
}
