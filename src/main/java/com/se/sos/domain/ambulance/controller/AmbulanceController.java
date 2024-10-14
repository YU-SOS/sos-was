package com.se.sos.domain.ambulance.controller;

import com.se.sos.domain.ambulance.service.AmbulanceService;
import com.se.sos.domain.paramedic.dto.ParamedicReq;
import com.se.sos.global.response.success.SuccessRes;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/ambulance")
public class AmbulanceController {
    private final AmbulanceService ambulanceService;

    @GetMapping("/{ambulanceId}")
    public ResponseEntity<?> getAmbulance(@PathVariable(name = "ambulanceId") UUID ambulanceId) {
        return ResponseEntity.ok().body(SuccessRes.from(ambulanceService.getAmbulanceById(ambulanceId)));
    }

    @PostMapping("/{ambulanceId}/member")
    public ResponseEntity<?> addParamedic(
            @PathVariable(name = "ambulanceId") UUID id,
            @Valid @RequestBody ParamedicReq paramedicReq
    ) {
        return ambulanceService.addParamedic(id, paramedicReq);
    }

    @PutMapping("/{ambulanceId}/member/{memberId}")
    public ResponseEntity<?> updateParamedic(
            @PathVariable(name = "ambulanceId") UUID ambulanceId,
            @PathVariable(name = "memberId") UUID memberId,
            @Valid @RequestBody ParamedicReq paramedicReq
    ) {
        return ambulanceService.updateParamedic(ambulanceId, memberId, paramedicReq);
    }

    @DeleteMapping("/{ambulanceId}/member/{memberId}")
    public ResponseEntity<?> deleteParamedic(
            @PathVariable(name = "ambulanceId") UUID ambulanceId,
            @PathVariable(name = "memberId") UUID memberId
    ) {
        return ambulanceService.deleteParamedic(ambulanceId, memberId);
    }
}
