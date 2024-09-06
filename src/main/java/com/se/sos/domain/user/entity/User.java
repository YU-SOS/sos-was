package com.se.sos.domain.user.entity;

import com.se.sos.global.common.role.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@NoArgsConstructor
@Getter
@Table(name = "USERS") // H2 error
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotNull
    private String name;

    @NotNull
    private String providerUserInfo;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Role role;

    private String email;

    @Builder
    public User(String name, String provider, String providerId, String email) {
        this.name = name;
        this.providerUserInfo = provider + " " + providerId;
        this.email = email;
        this.role = Role.USER;
    }

    public void updateName(String name){
        this.name = name;
    }
}
