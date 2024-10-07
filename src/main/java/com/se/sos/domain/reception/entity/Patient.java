package com.se.sos.domain.reception.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
public class Patient {  // 환자
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID id;

    @NotNull
    String name;

    @NotNull
    int age;

    @NotNull
    String phoneNumber;

    @NotNull
    String symptom; // 증상

    @NotNull
    String medication; // 복용약 List

    String reference; // 특이사항

    @Enumerated(EnumType.STRING)
    Gender gender;


    @Builder
    public Patient(String name, int age, String phoneNumber, String symptom,
                   String medication, String reference, Gender gender)
    {
        this.name = name;
        this.age = age;
        this.phoneNumber = phoneNumber;
        this.symptom = symptom;
        this.medication = medication;
        this.reference = reference;
        this.gender = gender;
    }
}
