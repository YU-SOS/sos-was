package com.se.sos.domain.admin.entity;

import com.se.sos.domain.user.entity.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Admin {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID id;

    String adminId;
    String password;

    @Enumerated(EnumType.STRING)
    Role role;

    @Builder
    public Admin(String adminId, String password) {
        this.adminId = adminId;
        this.password = password;
        this.role = Role.ADMIN;
    }
}
