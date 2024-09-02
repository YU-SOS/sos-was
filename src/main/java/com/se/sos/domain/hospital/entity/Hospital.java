package com.se.sos.domain.hospital.entity;

import com.se.sos.global.common.role.Role;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Hospital {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String hospitalId;
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Builder
    public Hospital(String hospitalId, String password, Role role) {
        this.hospitalId = hospitalId;
        this.password = password;
        this.role = role;
    }

}
