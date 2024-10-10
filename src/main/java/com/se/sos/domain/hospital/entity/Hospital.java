package com.se.sos.domain.hospital.entity;

import com.se.sos.domain.ambulance.entity.Location;
import com.se.sos.domain.hospital.dto.HospitalUpdateReq;
import com.se.sos.domain.reception.entity.Reception;
import com.se.sos.domain.user.entity.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import com.se.sos.domain.category.entity.Category;
import com.se.sos.domain.category.entity.CategoryHospital;
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
    private UUID id;

    @NotNull
    private String hospitalId;

    @NotNull
    private String password;

    @NotNull
    private String name;

    @NotNull
    private String address;

    @NotNull
    private String telephoneNumber;

    @NotNull
    @Embedded
    private Location location;

    private String imageUrl;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Enumerated(EnumType.STRING)
    private EmergencyRoomStatus emergencyRoomStatus = EmergencyRoomStatus.AVAILABLE;

    @OneToMany(mappedBy = "hospital")
    List<Reception> receptions = new ArrayList<>();

    @OneToMany(mappedBy = "hospital",cascade = CascadeType.ALL, orphanRemoval = true)
    List<CategoryHospital> categoryHospitals = new ArrayList<>();

    @Builder
    public Hospital(String hospitalId, String password, Role role, String name, String address, String telephoneNumber,
                    String imageUrl, Location location, EmergencyRoomStatus emergencyRoomStatus) {
        this.hospitalId = hospitalId;
        this.password = password;
        this.role = role;
        this.name = name;
        this.address = address;
        this.telephoneNumber = telephoneNumber;
        this.imageUrl = imageUrl;
        this.location = location;
        this.emergencyRoomStatus = emergencyRoomStatus;
    }
    public void addCategories(List<Category> categories) {
        for (Category category : categories) {
            CategoryHospital categoryHospital = CategoryHospital.builder()
                    .category(category)
                    .hospital(this)
                    .build();
            this.categoryHospitals.add(categoryHospital);
        }
    }

    public void updateHospital(HospitalUpdateReq hospitalUpdateReq) {
        this.name = hospitalUpdateReq.getName();
        this.address = hospitalUpdateReq.getAddress();
        this.telephoneNumber = hospitalUpdateReq.getTelephoneNumber();
        this.imageUrl = hospitalUpdateReq.getImageUrl();
        this.location = hospitalUpdateReq.getLocation();
    }

    public void updateRole(Role role){
        this.role = role;
    }

    public void updateEmergencyStatus(boolean emergencyStatus) {
        if(emergencyStatus){
            this.emergencyRoomStatus = EmergencyRoomStatus.AVAILABLE;
        }else{
            this.emergencyRoomStatus = EmergencyRoomStatus.FULL;
        }
    }

    public void updateCategories(List<Category> categories) {
        this.categoryHospitals.clear();
        addCategories(categories);
    }
}
