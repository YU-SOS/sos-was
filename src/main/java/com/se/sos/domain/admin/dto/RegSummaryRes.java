package com.se.sos.domain.admin.dto;

import com.se.sos.domain.ambulance.entity.Ambulance;
import com.se.sos.domain.hospital.entity.Hospital;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.UUID;

@Schema(name = "회원가입 요청 요약 Res", description = "회원가입 목록 조회 시 요약 정보를 담은 응답 DTO")
public record RegSummaryRes(
        @Schema(description = "요청자 고유번호")
        UUID id,
        @Schema(description = "요청자 이름")
        String name,
        @Schema(description = "요청자 주소")
        String address,
        @Schema(description = "요청자 전화번호")
        String telephoneNumber
) {
    public static RegSummaryRes fromHospital(Hospital hospital){
        return new RegSummaryRes(hospital.getId(), hospital.getName(), hospital.getAddress(), hospital.getTelephoneNumber());
    }

    public static RegSummaryRes fromAmbulance(Ambulance ambulance){
        return new RegSummaryRes(ambulance.getId(), ambulance.getName(), ambulance.getAddress(), ambulance.getTelephoneNumber());
    }


}
