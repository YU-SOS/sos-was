package com.se.sos.domain.ambulance.entity;

import com.se.sos.domain.paramedic.entity.Paramedic;
import com.se.sos.domain.reception.entity.Reception;
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

    String loginId;
    String password;

    String name;
    String address;
    String telephoneNumber;

    // 위치 (경도 위도)

    @OneToMany(mappedBy = "ambulance")
    List<Paramedic> paramedics = new ArrayList<>();

    @OneToMany(mappedBy = "ambulance")
    List<Reception> receptions = new ArrayList<>();

    @Builder
    public Ambulance(String name, String address, String telephoneNumber) {
        this.name = name;
        this.address = address;
        this.telephoneNumber = telephoneNumber;
    }
}
