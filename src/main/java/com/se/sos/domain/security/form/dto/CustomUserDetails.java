package com.se.sos.domain.security.form.dto;

import com.se.sos.domain.user.entity.Role;
import org.springframework.security.core.userdetails.UserDetails;

public interface CustomUserDetails extends UserDetails {
    String getId();

    boolean hasRole(Role role);
}
