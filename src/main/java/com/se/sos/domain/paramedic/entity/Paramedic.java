package com.se.sos.domain.paramedic.entity;

import com.se.sos.domain.ambulance.entity.Ambulance;
import com.se.sos.domain.paramedic.dto.ParamedicReq;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

import static jakarta.persistence.FetchType.LAZY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
public class Paramedic {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID id;

    String name;
    String phoneNumber;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "AMBULANCE_ID")
    Ambulance ambulance;

    @Builder
    public Paramedic(String name, String phoneNumber, Ambulance ambulance) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.ambulance = ambulance;
    }

    public void updateInfo(ParamedicReq paramedicReq){
        this.name = paramedicReq.name();
        this.phoneNumber = paramedicReq.phoneNumber();
    }
}
