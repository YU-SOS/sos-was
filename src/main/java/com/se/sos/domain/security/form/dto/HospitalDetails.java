package com.se.sos.domain.security.form.dto;

import com.se.sos.domain.hospital.entity.Hospital;
import com.se.sos.domain.user.entity.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.Collection;

@RequiredArgsConstructor
public class HospitalDetails implements CustomUserDetails {

    private final Hospital hospital;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> authorities = new ArrayList<>();

        authorities.add(new SimpleGrantedAuthority(hospital.getRole().getValue()));

        return authorities;
    }

    @Override
    public String getPassword() {
        return hospital.getPassword();
    }

    @Override
    public String getUsername() {
        return hospital.getHospitalId();
    }

    @Override
    public String getId() {
        return hospital.getId().toString();
    }

    @Override
    public boolean hasRole(Role role) {
        return role.equals(hospital.getRole());
    }
}
