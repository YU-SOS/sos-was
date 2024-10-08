package com.se.sos.domain.reception.entity;

import com.se.sos.domain.ambulance.entity.Ambulance;
import com.se.sos.domain.comment.entity.Comment;
import com.se.sos.domain.hospital.entity.Hospital;
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
    TransferStatus status = TransferStatus.MOVE;

    @Enumerated(EnumType.STRING)
    ReceptionStatus receptionStatus = ReceptionStatus.PENDING;

    @NotNull
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

    @OneToMany(mappedBy = "reception", cascade = CascadeType.ALL, orphanRemoval = true)
    List<Comment> commentList = new ArrayList<>();

    @Builder
    public Reception(
            LocalDateTime startTime,
            Ambulance ambulance,
            Hospital hospital,
            Patient patient) {
        this.startTime = startTime;
        this.ambulance = ambulance;
        this.patient = patient;
        this.hospital = hospital;
    }

    public void updateReceptionStatus(ReceptionStatus newStatus) {
        this.receptionStatus = newStatus;
    }

}
