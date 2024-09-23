package com.se.sos.domain.category.entity;

import com.se.sos.domain.hospital.entity.Hospital;
import jakarta.persistence.*;
import lombok.Builder;

import java.util.UUID;

import static jakarta.persistence.FetchType.LAZY;

@Entity
public class CategoryHospital {

    @Id @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "CATEGORY_HOSPITAL_ID")
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
