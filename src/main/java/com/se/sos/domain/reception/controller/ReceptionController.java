package com.se.sos.domain.reception.controller;

import com.se.sos.domain.reception.dto.ReceptionCreateReq;
import com.se.sos.domain.reception.service.ReceptionService;
import com.se.sos.global.util.jwt.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Controller
@RequestMapping("/reception")
@RequiredArgsConstructor
public class ReceptionController {
    private final ReceptionService receptionService;
    private final JwtUtil jwtUtil;

    @PostMapping
    public ResponseEntity<?> createReception(@RequestHeader("Authorization") String authorizationHeader,
                                             @RequestBody ReceptionCreateReq receptionCreateDto) {
        String token = authorizationHeader.substring(7);
        UUID ambulanceId = jwtUtil.getIdFromToken(token);
        receptionService.createReception(receptionCreateDto, ambulanceId);

        return ResponseEntity.ok().build();
    }
    @GetMapping("/{receptionId}")
    public ResponseEntity<?> getReceptionDetail(@PathVariable(name = "receptionId") String id) {
        return ResponseEntity.ok().body(receptionService.findReceptionById(id));
    }
}
