package com.se.sos.domain.security.form.service;

import com.se.sos.domain.ambulance.entity.Ambulance;
import com.se.sos.domain.ambulance.repository.AmbulanceRepository;
import com.se.sos.domain.hospital.entity.Hospital;
import com.se.sos.domain.hospital.repository.HospitalRepository;
import com.se.sos.domain.security.form.dto.AmbulanceDetails;
import com.se.sos.domain.security.form.dto.HospitalDetails;
import com.se.sos.global.exception.CustomException;
import com.se.sos.global.response.error.ErrorType;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AmbulanceAndHospitalDetailsService implements UserDetailsService {

    private final HospitalRepository hospitalRepository;
    private final AmbulanceRepository ambulanceRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        String role = username.split(" ")[0];
        String id = username.split(" ")[1];

        if(role.equals("AMB")){

            Ambulance ambulance = ambulanceRepository.findByAmbulanceId(id);
            // Optional 설정?
            
            return new AmbulanceDetails(ambulance);

        } else if( role.equals("HOS")){
            Hospital hospital = hospitalRepository.findByHospitalId(id)
                    .orElseThrow(() -> new CustomException(ErrorType.HOSPITAL_NOT_FOUND));

            return new HospitalDetails(hospital);

        }

        return null;
    }
}
