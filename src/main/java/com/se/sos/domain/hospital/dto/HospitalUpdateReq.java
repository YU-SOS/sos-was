package com.se.sos.domain.hospital.dto;

import com.se.sos.domain.ambulance.entity.Location;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;


@Getter
public class HospitalUpdateReq {
    @NotBlank
    private String name;
    @NotBlank
    private String address;
    @NotBlank
    private String telephoneNumber;
    @NotBlank
    private String imageUrl;
    @NotNull
    private Location location;
}
