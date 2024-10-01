package com.se.sos.domain.security.form.dto;

import com.se.sos.domain.user.entity.Role;
import com.se.sos.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.Collection;

@RequiredArgsConstructor
public class SecurityUserDetails implements CustomUserDetails {

    private final User user;

    @Override
    public String getId() {
        return user.getId().toString();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        Collection<GrantedAuthority> authorities = new ArrayList<>();

        authorities.add(new SimpleGrantedAuthority(user.getRole().getRole()));

        return authorities;

    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return user.getEmail(); // user의 경우에는 email 리턴
    }

    @Override
    public boolean hasRole(Role role){
        return role.equals(user.getRole());
    }
}
