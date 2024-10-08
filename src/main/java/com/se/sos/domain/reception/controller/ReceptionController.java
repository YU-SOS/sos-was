package com.se.sos.domain.reception.controller;

import com.se.sos.domain.reception.dto.ReceptionCreateReq;
import com.se.sos.domain.reception.dto.PatientWrapperRes;
import com.se.sos.domain.reception.dto.VisitReq;
import com.se.sos.domain.reception.entity.ReceptionStatus;
import com.se.sos.domain.reception.service.ReceptionService;
import com.se.sos.global.exception.CustomException;
import com.se.sos.global.response.error.ErrorType;
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

    @PutMapping("/{receptionId}")
    public ResponseEntity<?> handleVisitRequest(@PathVariable(name = "receptionId") String id,
                                                @RequestBody VisitReq visitReq) {
        if(visitReq.getReceptionStatus().equals(ReceptionStatus.APPROVED)){
            return ResponseEntity.ok().body(receptionService.approvedVisitRequest(id));
        } else if (visitReq.getReceptionStatus().equals(ReceptionStatus.REJECTED)) {
            return ResponseEntity.ok().body(new PatientWrapperRes(visitReq.getReceptionStatus(),receptionService.rejectedVisitRequest(id)));
        }
        throw new CustomException(ErrorType.INVALID_RECEPTION_STATUS);
    }
}
