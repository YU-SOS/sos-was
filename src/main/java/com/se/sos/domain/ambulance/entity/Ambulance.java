package com.se.sos.domain.ambulance.entity;

import com.se.sos.domain.paramedic.entity.Paramedic;
import com.se.sos.domain.reception.entity.Reception;
import com.se.sos.global.common.role.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
public class Ambulance {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID id;

    @NotNull
    String name;

    @NotNull
    String address;

    @NotNull
    String telephoneNumber;

    @NotNull
    String ambulanceId;

    @NotNull
    String password;

    @Enumerated(EnumType.STRING)
    Role role;

    @NotNull
    @Embedded
    Location location;

    String imageUrl;


    @OneToMany(mappedBy = "ambulance")
    List<Paramedic> paramedics = new ArrayList<>();

    @OneToMany(mappedBy = "ambulance")
    List<Reception> receptions = new ArrayList<>();


    @Builder
    public Ambulance(
            String name,
            String address,
            String telephoneNumber,
            String ambulanceId,
            String password,
            Role role,
            Location location,
            String imageUrl
    ) {
        this.name = name;
        this.address = address;
        this.telephoneNumber = telephoneNumber;
        this.ambulanceId = ambulanceId;
        this.password = password;
        this.role = role;
        this.location = location;
        this.imageUrl = imageUrl;
    }
}
