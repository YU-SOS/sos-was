package com.se.sos.domain.reception.controller;

import com.se.sos.domain.comment.dto.CommentReq;
import com.se.sos.domain.reception.api.ReceptionAPI;
import com.se.sos.domain.reception.dto.ReceptionApproveReq;
import com.se.sos.domain.reception.dto.ReceptionCreateReq;
import com.se.sos.domain.reception.dto.ReceptionReVisitReq;
import com.se.sos.domain.reception.service.ReceptionService;
import com.se.sos.global.response.success.SuccessRes;
import com.se.sos.global.response.success.SuccessType;
import com.se.sos.global.util.jwt.JwtUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/reception")
@RequiredArgsConstructor
public class ReceptionController implements ReceptionAPI {
    private final ReceptionService receptionService;
    private final JwtUtil jwtUtil;

    @PostMapping
    public ResponseEntity<?> createReception(
            @RequestHeader("Authorization") String authorizationHeader,
            @Valid @RequestBody ReceptionCreateReq receptionCreateDto
    ) {
        String token = authorizationHeader.substring(7);
        UUID ambulanceId = jwtUtil.getIdFromToken(token);


        return ResponseEntity.status(SuccessType.CREATED.getStatus())
                .body(SuccessRes.of(SuccessType.CREATED, receptionService.createReception(receptionCreateDto, ambulanceId)));
    }


    @GetMapping("/{receptionId}")
    public ResponseEntity<?> getReception(@PathVariable(name = "receptionId") UUID id) {
        return ResponseEntity.ok()
                .body(SuccessRes.from(receptionService.findReceptionById(id)));
    }

    @GetMapping("/{receptionNumber}/user")
    public ResponseEntity<?> getReceptionForUser(@PathVariable(name = "receptionNumber") String number){
        return ResponseEntity.ok()
                .body(SuccessRes.from(receptionService.findReceptionByNumber(number)));
    }

    @GetMapping("/{receptionNumber}/guest")
    public ResponseEntity<?> getReceptionForGuest(@PathVariable(name = "receptionNumber") String number) {
        return ResponseEntity.ok()
                .body(SuccessRes.from(receptionService.findReceptionForGuest(number)));
    }

    /* 병원 - 해당 reception 수락/거절 */
    @PutMapping("/{receptionId}")
    public ResponseEntity<?> acceptReceptionRequest(
            @PathVariable(name = "receptionId") UUID id,
            @Valid @RequestBody ReceptionApproveReq req
    ) {
        receptionService.acceptReception(id, req.isApproved());

        return ResponseEntity.ok()
                .body(SuccessRes.from(SuccessType.OK));
    }

    /* 구급대 - 재요청 */
    @PutMapping("/{receptionId}/re")
    public ResponseEntity<?> retryReceptionRequest(
            @PathVariable(name = "receptionId") UUID id,
            @Valid @RequestBody ReceptionReVisitReq req
    ){
        return ResponseEntity.ok().
                body(SuccessRes.of(SuccessType.OK, receptionService.reRequestReception(id, req.hospitalId())));
    }

    /* 병원 - 도착 완료 */
    @PutMapping("/{receptionId}/arrival")
    public ResponseEntity<?> closeReception(
            @PathVariable(name = "receptionId") UUID id
    ){
        receptionService.closeReception(id);

        return ResponseEntity.ok().
                body(SuccessRes.from(SuccessType.OK));
    }

    @PostMapping("/{receptionId}/comment")
    public ResponseEntity<?> addComment(
            @PathVariable(name = "receptionId") UUID id,
            @Valid @RequestBody CommentReq req
    ) {
        receptionService.addComment(id, req);
        return ResponseEntity.status(SuccessType.COMMENT_CREATED.getStatus()).
                body(SuccessRes.from(SuccessType.COMMENT_CREATED));
    }
}
