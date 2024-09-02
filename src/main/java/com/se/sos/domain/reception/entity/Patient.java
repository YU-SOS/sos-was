package com.se.sos.domain.reception.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
public class Patient {  // 환자
    @Id
    @GeneratedValue
    Long id;

    String name;
    int age;
    String number;
    String symptom; // 증상
    String medication; // 복용약
    String reference; // 특이사항

    @Enumerated(EnumType.STRING)
    Gender gender;

    @Enumerated(EnumType.STRING)
    TransferStatus status;

    @Builder
    public Patient(String name, int age, String number, String symptom,
                   String medication, String reference, Gender gender,
                   TransferStatus status) {
        this.name = name;
        this.age = age;
        this.number = number;
        this.symptom = symptom;
        this.medication = medication;
        this.reference = reference;
        this.gender = gender;
        this.status = status;
    }
}
