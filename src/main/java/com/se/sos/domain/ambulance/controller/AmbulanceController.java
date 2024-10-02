package com.se.sos.domain.ambulance.controller;

import com.se.sos.domain.ambulance.service.AmbulanceService;
import com.se.sos.domain.paramedic.dto.ParamedicRegisterReq;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/ambulance")
public class AmbulanceController {
    private final AmbulanceService ambulanceService;

    @PostMapping("/{ambulanceId}/member")
    public ResponseEntity<?> addParamedic(
            @PathVariable(name = "ambulanceId") UUID id,
            @RequestBody ParamedicRegisterReq paramedicRegisterReq
            ) {
        return ambulanceService.addParamedic(id, paramedicRegisterReq);
    }
}
