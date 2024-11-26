package com.se.sos.domain.hospital.controller;

import com.se.sos.domain.hospital.api.HospitalAPI;
import com.se.sos.domain.hospital.dto.HospitalUpdateReq;
import com.se.sos.domain.hospital.service.HospitalService;
import com.se.sos.global.response.success.SuccessRes;
import com.se.sos.global.response.success.SuccessType;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import static lombok.AccessLevel.PROTECTED;

@RestController
@RequestMapping("/hospital")
@RequiredArgsConstructor(access = PROTECTED)
public class HospitalController implements HospitalAPI {

    private final HospitalService hospitalService;

    @GetMapping
    public ResponseEntity<?> getAllHospitals(
            @RequestParam(name = "categories", required = false) List<String> categories,
            @PageableDefault(size = 10) Pageable pageable
    ){
        if (categories == null || categories.isEmpty())
            return ResponseEntity.ok().body(SuccessRes.from(hospitalService.getAllHospitals(pageable)));
        return ResponseEntity.ok().body(SuccessRes.from(hospitalService.getAllHospitalsByCategories(categories, pageable)));
    }

    @GetMapping("/user")
    public ResponseEntity<?> getAllHospitalsForUser(){
        return ResponseEntity.ok()
                .body(SuccessRes.from(hospitalService.getAllHospitals()));
    }

    @GetMapping("/{hospitalId}")
    public ResponseEntity<?> getHospitalDetails(@PathVariable(name = "hospitalId") UUID id){
        return ResponseEntity.ok()
                .body(SuccessRes.from(hospitalService.findHospitalById(id)));
    }
    @GetMapping("/{hospitalId}/reception/accept")
    public ResponseEntity<?> getAllHospitalReceptionAccept(@PathVariable(name = "hospitalId") UUID id){
        return ResponseEntity.ok()
                .body(SuccessRes.from(hospitalService.getReceptionByHospitalAndStatus(id)));
    }

    @GetMapping("/{hospitalId}/reception")
    public ResponseEntity<?> getAllReceptionsByHospitalId(
            @PathVariable(name = "hospitalId") UUID id,
            @PageableDefault(size = 10) Pageable pageable
    ){
        return ResponseEntity.ok()
                .body(SuccessRes.from(hospitalService.findReceptionsById(id,pageable)));
    }

    @PutMapping("/{hospitalId}")
    public ResponseEntity<?> updateHospital(
            @PathVariable(name = "hospitalId") UUID id,
            @RequestBody HospitalUpdateReq hospitalUpdateReq
    ){
        hospitalService.updateHospitalById(id,hospitalUpdateReq);
        return ResponseEntity.ok()
                .body(SuccessRes.from(SuccessType.OK));
    }

    @PutMapping("/{hospitalId}/emergencyStatus")
    public ResponseEntity<?> updateStatus(
            @PathVariable(name = "hospitalId") UUID id,
            @RequestParam(name = "emergencyStatus") boolean emergencyStatus
    ){
        hospitalService.updateEmergencyStatus(id,emergencyStatus);
        return ResponseEntity.ok()
                .body(SuccessRes.from(SuccessType.OK));
    }
}
