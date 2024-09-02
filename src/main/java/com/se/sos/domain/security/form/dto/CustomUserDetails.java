package com.se.sos.domain.security.form.dto;

import org.springframework.security.core.userdetails.UserDetails;

public interface CustomUserDetails extends UserDetails {
    String getId();
}
