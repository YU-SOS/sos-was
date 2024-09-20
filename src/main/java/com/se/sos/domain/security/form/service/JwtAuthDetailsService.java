package com.se.sos.domain.security.form.service;

import com.se.sos.domain.ambulance.entity.Ambulance;
import com.se.sos.domain.ambulance.repository.AmbulanceRepository;
import com.se.sos.domain.hospital.entity.Hospital;
import com.se.sos.domain.hospital.repository.HospitalRepository;
import com.se.sos.domain.security.form.dto.AmbulanceDetails;
import com.se.sos.domain.security.form.dto.HospitalDetails;
import com.se.sos.domain.security.form.dto.SecurityUserDetails;
import com.se.sos.domain.user.entity.User;
import com.se.sos.domain.user.repository.UserRepository;
import com.se.sos.global.common.role.Role;
import com.se.sos.global.exception.CustomException;
import com.se.sos.global.response.error.ErrorType;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Qualifier("jwtDetailsService")
@RequiredArgsConstructor
public class JwtAuthDetailsService implements UserDetailsService {
    private final HospitalRepository hospitalRepository;
    private final AmbulanceRepository ambulanceRepository;
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // role + subject(uuid)
        String role = username.split(" ")[0];
        UUID id =  UUID.fromString(username.split(" ")[1]);

        if(role.equals(Role.AMB.getRole()) || role.equals(Role.AMB_GUEST.getRole())){

            Ambulance ambulance = ambulanceRepository.findById(id)
                    .orElseThrow(() -> new CustomException(ErrorType.AMBULANCE_NOT_FOUND));

            return new AmbulanceDetails(ambulance);

        } else if( role.equals(Role.HOS.getRole()) || role.equals(Role.HOS_GUEST.getRole())){
            Hospital hospital = hospitalRepository.findById(id)
                    .orElseThrow(() -> new CustomException(ErrorType.HOSPITAL_NOT_FOUND));

            return new HospitalDetails(hospital);
        } else if ( role.equals(Role.USER.getRole())){
            User user = userRepository.findById(id)
                    .orElseThrow(() -> new CustomException(ErrorType.USER_NOT_FOUND));

            return new SecurityUserDetails(user);
        }

        return null;
    }
}
