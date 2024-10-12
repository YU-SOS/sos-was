package com.se.sos.domain.ambulance.entity;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class Location {
    @NotNull(message = "위도는 필수 입력값 입니다.")
    private String longitude;
    @NotNull(message = "경도는 필수 입력값 입니다.")
    private String latitude;
}
