package com.se.sos.domain.hospital.controller;

import com.se.sos.domain.hospital.dto.HospitalUpdateReq;
import com.se.sos.domain.hospital.service.HospitalService;
import com.se.sos.global.response.success.SuccessRes;
import com.se.sos.global.response.success.SuccessType;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import static lombok.AccessLevel.PROTECTED;

@Controller
@RequestMapping("/hospital")
@RequiredArgsConstructor(access = PROTECTED)
public class HospitalController {
    // 병원 목록 조회 , 응급실 상세 조회 , 응급실 정보 수정 - 수용 가능 불가 , 응급실 방문 신청 목록 조회

    private final HospitalService hospitalService;

    @GetMapping
    public ResponseEntity<?> getHospitals(@RequestParam(name = "categories", required = false) List<String> categories,
                                          @PageableDefault(size = 10) Pageable pageable,
                                          @RequestParam(name = "role", required = false) String role
    ) {
        if(role!= null && role.equals("user"))
            return ResponseEntity.ok().body(SuccessRes.of(SuccessType.OK, hospitalService.getAllHospitals()));

        if (categories == null || categories.isEmpty())
            return ResponseEntity.ok().body(SuccessRes.of(SuccessType.OK,hospitalService.getAllHospitals(pageable)));
        return ResponseEntity.ok().body(SuccessRes.of(SuccessType.OK,hospitalService.getHospitalsByCategories(categories, pageable)));
    }

    @GetMapping("/{hospitalId}")
    public ResponseEntity<?> getHospitalDetails(@PathVariable(name = "hospitalId") UUID id){
        return ResponseEntity.ok().body(SuccessRes.of(SuccessType.OK,hospitalService.findHospitalById(id)));
    }

    @GetMapping("/{hospitalId}/reception")
    public ResponseEntity<?> getReceptionsByHospital(@PathVariable(name = "hospitalId") UUID id,
                                           @PageableDefault(size = 10) Pageable pageable){
        return ResponseEntity.ok()
                .body(SuccessRes.of(SuccessType.OK,hospitalService.findReceptionsById(id,pageable)));
    }

    @PutMapping("/{hospitalId}")
    public ResponseEntity<?> updateHospital(@PathVariable(name = "hospitalId") UUID id,
                                            @RequestBody HospitalUpdateReq hospitalUpdateReq){
        return ResponseEntity.ok().body(SuccessRes.of(SuccessType.OK,hospitalService.updateHospitalById(id,hospitalUpdateReq)));
    }
    @PutMapping("/{hospitalId}/emergencyStatus")
    public ResponseEntity<?> updateStatus(@PathVariable(name = "hospitalId") UUID id,
                                          @RequestParam(name = "emergencyStatus") boolean emergencyStatus){
        return ResponseEntity.ok().body(SuccessRes.of(SuccessType.OK,hospitalService.updateEmergencyStatus(id,emergencyStatus)));
    }
}
