package com.se.sos.domain.security.form.dto;

import com.se.sos.domain.ambulance.entity.Ambulance;
import com.se.sos.domain.user.entity.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

@RequiredArgsConstructor
public class AmbulanceDetails implements CustomUserDetails {

    private final Ambulance ambulance;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> authorities = new ArrayList<>();

        authorities.add(new SimpleGrantedAuthority(ambulance.getRole().getValue()));

        return authorities;
    }

    @Override
    public String getPassword() {
        return ambulance.getPassword();
    }

    @Override
    public String getUsername() {
        return ambulance.getAmbulanceId();
    }

    @Override
    public String getId() {
        return ambulance.getId().toString();
    }

    @Override
    public boolean hasRole(Role role) {
        return role.equals(ambulance.getRole());
    }
}

