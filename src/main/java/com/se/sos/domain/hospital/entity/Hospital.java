package com.se.sos.domain.hospital.entity;

import com.se.sos.domain.reception.entity.Reception;
import com.se.sos.global.common.role.Role;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import com.se.sos.domain.category.entity.Category;
import com.se.sos.domain.category.entity.CategoryHospital;
import com.se.sos.domain.category.repository.CategoryHospitalRepository;
import com.se.sos.domain.comment.entity.Comment;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
public class Hospital {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String hospitalId;
    private String password;

    private String name;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Enumerated(EnumType.STRING)
    Speciality speciality; // 현재는 하나의 진료과목만 담을 수 있어서 추후 처리 필요

    @OneToMany(mappedBy = "hospital")
    List<Reception> receptions = new ArrayList<>();

    @Builder
    public Hospital(String hospitalId, String password, Role role) {
        this.hospitalId = hospitalId;
        this.password = password;
        this.role = role;
    }

}
