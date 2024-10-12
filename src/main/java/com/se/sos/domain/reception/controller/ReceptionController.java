package com.se.sos.domain.reception.controller;

import com.se.sos.domain.reception.dto.ReceptionApproveReq;
import com.se.sos.domain.reception.dto.ReceptionCreateReq;
import com.se.sos.domain.reception.dto.ReceptionReVisitReq;
import com.se.sos.domain.reception.service.ReceptionService;
import com.se.sos.global.response.success.SuccessRes;
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
    public ResponseEntity<?> createReception(
            @RequestHeader("Authorization") String authorizationHeader,
            @RequestBody ReceptionCreateReq receptionCreateDto
    ) {
        String token = authorizationHeader.substring(7);
        UUID ambulanceId = jwtUtil.getIdFromToken(token);
        receptionService.createReception(receptionCreateDto, ambulanceId);

        return ResponseEntity.ok().build();
    }


    @GetMapping("/{receptionId}")
    public ResponseEntity<?> getReception(@PathVariable(name = "receptionId") UUID id) {
        return ResponseEntity.ok().body(receptionService.findReceptionById(id));
    }

    @GetMapping("/{receptionId}/guest")
    public ResponseEntity<?> getReceptionForGuest(@PathVariable(name = "receptionId") UUID id) {
        return ResponseEntity.ok().body(SuccessRes.from(receptionService.findReceptionForGuest(id)));
    }

    /* 병원 - 해당 reception 수락/거절 */
    @PutMapping("/{receptionId}")
    public ResponseEntity<?> handleVisitRequest(
            @PathVariable(name = "receptionId") UUID id,
            @RequestBody ReceptionApproveReq req
    ) {
        receptionService.acceptReception(id, req.isApproved());

        return ResponseEntity.ok().build();
    }

    /* 구급대 - 재요청 */
    @PutMapping("/{receptionId}/re")
    public ResponseEntity<?> reVisitRequest(
            @PathVariable(name = "receptionId") UUID id,
            @RequestBody ReceptionReVisitReq req
            ){
        receptionService.reRequestReception(id, req.hospitalId());

        return ResponseEntity.ok().build();
    }

    /* 병원 - 도착 완료 */
    @PutMapping("/{receptionId}/arrival")
    public ResponseEntity<?> closeReception(
            @PathVariable(name = "receptionId") UUID id
    ){
        receptionService.closeReception(id);

        return ResponseEntity.ok().build();
    }

}
