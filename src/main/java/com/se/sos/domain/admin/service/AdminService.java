package com.se.sos.domain.admin.service;

import com.se.sos.domain.admin.dto.RegSummaryRes;
import com.se.sos.domain.admin.dto.SystemStatusRes;
import com.se.sos.domain.ambulance.dto.AmbulanceRegRes;
import com.se.sos.domain.ambulance.entity.Ambulance;
import com.se.sos.domain.ambulance.repository.AmbulanceRepository;
import com.se.sos.domain.category.entity.CategoryHospital;
import com.se.sos.domain.category.repository.CategoryRepository;
import com.se.sos.domain.hospital.dto.HospitalRegRes;
import com.se.sos.domain.hospital.entity.Hospital;
import com.se.sos.domain.hospital.repository.HospitalRepository;
import com.se.sos.domain.reception.repository.ReceptionRepository;
import com.se.sos.domain.user.entity.Role;
import com.se.sos.domain.user.repository.UserRepository;
import com.se.sos.global.exception.CustomException;
import com.se.sos.global.response.error.ErrorType;
import com.se.sos.global.response.success.SuccessRes;
import com.se.sos.global.response.success.SuccessType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class AdminService {

    private final AmbulanceRepository ambulanceRepository;;
    private final HospitalRepository hospitalRepository;
    private final UserRepository userRepository;
    private final ReceptionRepository receptionRepository;
    private final CategoryRepository categoryRepository;

    public Map<String, List<RegSummaryRes>> getAllRegistration(){

        List<RegSummaryRes> ambulanceList = ambulanceRepository.findByRole(Role.AMB_GUEST)
                .stream()
                .map(amb -> RegSummaryRes.fromAmbulance(amb))
                .toList();

        List<RegSummaryRes> hospitalList = hospitalRepository.findByRole(Role.HOS_GUEST)
                .stream()
                .map(hos -> RegSummaryRes.fromHospital(hos))
                .toList();

        Map<String, List<RegSummaryRes>> map = new HashMap<>();

        map.put("ambulanceList", ambulanceList);
        map.put("hospitalList", hospitalList);

        return map;
    }

    public AmbulanceRegRes getAmbulanceRegistration(UUID id){
        Ambulance ambulance = ambulanceRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorType.AMBULANCE_NOT_FOUND));

        return AmbulanceRegRes.fromEntity(ambulance);
    }

    public HospitalRegRes getHospitalRegistration(UUID id){
        Hospital hospital = hospitalRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorType.HOSPITAL_NOT_FOUND));

        return HospitalRegRes.fromEntity(hospital, hospital.getCategories());
    }

    @Transactional
    public void approveRegistration(Role role, UUID id, boolean isApproved){
        if(role.equals(Role.AMB_GUEST)){
            Ambulance ambulance = ambulanceRepository.findById(id)
                    .orElseThrow(() -> new CustomException(ErrorType.AMBULANCE_NOT_FOUND));

            if(isApproved)
                ambulance.updateRole(Role.AMB);
            else
                ambulance.updateRole(Role.BLACKLIST);

            ambulanceRepository.save(ambulance);

        } else if (role.equals(Role.HOS_GUEST)){
            Hospital hospital = hospitalRepository.findById(id)
                    .orElseThrow(() -> new CustomException(ErrorType.HOSPITAL_NOT_FOUND));

            if(isApproved)
                hospital.updateRole(Role.HOS);
            else
                hospital.updateRole(Role.BLACKLIST);

            hospitalRepository.save(hospital);
        } else {
            log.error("Role 값 불일치");
            throw new CustomException(ErrorType.BAD_REQUEST);
        }

    }

    public SystemStatusRes getSystemStatus(){
        return SystemStatusRes.builder()
                .ambulanceCount(ambulanceRepository.countByRole(Role.AMB))
                .hospitalCount(hospitalRepository.countByRole(Role.HOS))
                .userCount(userRepository.count())
                .receptionCount(receptionRepository.count())
                .build();
    }
}
