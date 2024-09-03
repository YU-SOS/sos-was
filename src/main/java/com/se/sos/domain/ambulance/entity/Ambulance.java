package com.se.sos.domain.ambulance.entity;

import com.se.sos.domain.paramedic.entity.Paramedic;
//import com.se.sos.domain.reception.entity.Reception;
import com.se.sos.domain.reception.entity.Reception;
import com.se.sos.global.common.role.Role;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.*;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
public class Ambulance {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    Long id;

    String name;
    String address;
    String telephoneNumber;

    String ambulanceId;
    String password;

    @Enumerated(EnumType.STRING)
    Role role;


    // 위치 (경도 위도)

    @OneToMany(mappedBy = "ambulance")
    List<Paramedic> paramedics = new ArrayList<>();

    @OneToMany(mappedBy = "ambulance")
    List<Reception> receptions = new ArrayList<>();

//    @Builder
//    public Ambulance(String name, String address, String telephoneNumber, String ambulanceId, String password, String role) {
//        this.name = name;
//        this.address = address;
//        this.telephoneNumber = telephoneNumber;
//    }

    @Builder
    public Ambulance(String ambulanceId, String password, Role role) {
        this.ambulanceId = ambulanceId;
        this.password = password;
        this.role = role;
    }
}
