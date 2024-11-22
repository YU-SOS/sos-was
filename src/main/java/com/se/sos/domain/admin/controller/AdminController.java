package com.se.sos.domain.admin.controller;

import com.se.sos.domain.admin.api.AdminAPI;
import com.se.sos.domain.admin.dto.RegApproveReq;
import com.se.sos.domain.admin.service.AdminService;
import com.se.sos.domain.user.entity.Role;
import com.se.sos.global.exception.CustomException;
import com.se.sos.global.response.error.ErrorRes;
import com.se.sos.global.response.error.ErrorType;
import com.se.sos.global.response.success.SuccessRes;
import com.se.sos.global.response.success.SuccessType;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController implements AdminAPI {

    private final AdminService adminService;

    @GetMapping("/registrations")
    public ResponseEntity<?> getAllRegistrations(){
        return ResponseEntity.ok().body(SuccessRes.from(adminService.getAllRegistration()));
    }

    @GetMapping("/registration")
    public ResponseEntity<?> getRegistration(
            @RequestParam(name = "role", required = true) Role role,
            @RequestParam(name = "id", required = true) UUID id
    ){
        if(role.equals(Role.AMB_GUEST))
            return ResponseEntity.ok().body(SuccessRes.from(adminService.getAmbulanceRegistration(id)));
        else if(role.equals(Role.HOS_GUEST))
            return ResponseEntity.ok().body(SuccessRes.from(adminService.getHospitalRegistration(id)));
        else
            throw new CustomException(ErrorType.ROLE_MISMATCH);

    }

    @PutMapping("/registration")
    public ResponseEntity<?> approveRegistration(
            @RequestParam(name = "role") Role role,
            @RequestParam(name = "id") UUID id,
            @RequestBody RegApproveReq regApproveReq
    ){
        adminService.approveRegistration(role, id, regApproveReq.isApproved());
        return ResponseEntity.ok().body(SuccessRes.from(SuccessType.OK));
    }

    @GetMapping("/status")
    public ResponseEntity<?> getSystemStatus(){
        return ResponseEntity.ok().body(SuccessRes.from(adminService.getSystemStatus()));
    }
}
