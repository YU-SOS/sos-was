package com.se.sos.domain.reception.entity;

import com.se.sos.domain.ambulance.entity.Ambulance;
import com.se.sos.domain.comment.entity.Comment;
import com.se.sos.domain.hospital.entity.Hospital;
import com.se.sos.domain.paramedic.entity.Paramedic;
import com.se.sos.domain.patient.entity.Patient;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static jakarta.persistence.FetchType.LAZY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
public class Reception {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID id;

    @Enumerated(EnumType.STRING)
    ReceptionStatus receptionStatus = ReceptionStatus.PENDING;

    @NotNull
    @Column(length = 6, unique = true)
    String number;

    @NotNull
    LocalDateTime startTime; // 이송 시작 시간
    LocalDateTime endTime; // 이송 완료 시간

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "AMBULANCE_ID")
    Ambulance ambulance;

    @OneToOne(fetch = LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "PATIENT_ID")
    Patient patient;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "HOSPITAL_ID")
    Hospital hospital;

    @ManyToOne(fetch = LAZY, cascade = CascadeType.REMOVE)
    @JoinColumn(name = "PARAMEDIC_ID")
    Paramedic paramedic;

    @OneToMany(mappedBy = "reception", cascade = CascadeType.ALL, orphanRemoval = true)
    List<Comment> commentList = new ArrayList<>();

    @Builder
    public Reception(
            LocalDateTime startTime,
            Ambulance ambulance,
            Hospital hospital,
            Patient patient,
            Paramedic paramedic,
            String number) {
        this.startTime = startTime;
        this.ambulance = ambulance;
        this.patient = patient;
        this.hospital = hospital;
        this.paramedic = paramedic;
        this.number = number;
    }

    public void updateReceptionStatus(ReceptionStatus status) {
        this.receptionStatus = status;
    }

    public void updateHospital(Hospital hospital){
        this.receptionStatus = ReceptionStatus.PENDING;
        this.hospital = hospital;
    }

    public void closeReception(){
        this.receptionStatus = ReceptionStatus.ARRIVAL;
    }

}
