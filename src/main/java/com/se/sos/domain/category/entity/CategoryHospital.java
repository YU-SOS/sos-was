package com.se.sos.domain.category.entity;

import com.se.sos.domain.hospital.entity.Hospital;
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
public class CategoryHospital {

    @Id @GeneratedValue(strategy = GenerationType.UUID)
    @Column
    UUID id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "CATEGORY_ID")
    Category category;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "HOSPITAL_ID")
    Hospital hospital;

    @Builder
    public CategoryHospital(Category category, Hospital hospital) {
        this.category = category;
        this.hospital = hospital;
    }
}
