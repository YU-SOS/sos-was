package com.se.sos.domain.reception.entity;

import com.se.sos.domain.ambulance.entity.Ambulance;
import com.se.sos.domain.hospital.entity.Hospital;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
public class Reception {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    Long id;

    @Enumerated(EnumType.STRING)
    TransferStatus status = TransferStatus.MOVE;

    LocalDateTime startTime; // 이송 시작 시간
    LocalDateTime endTime; // 이송 완료 시간

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "AMBULANCE_ID")
    Ambulance ambulance;

    @OneToOne(fetch = LAZY)
    @JoinColumn(name = "PATIENT_ID")
    Patient patient;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "HOSPITAL_ID")
    Hospital hospital;

    @Builder
    public Reception(LocalDateTime startTime, Ambulance ambulance,
                     Patient patient) {
        this.startTime = startTime;
        this.ambulance = ambulance;
        this.patient = patient;
    }
}
