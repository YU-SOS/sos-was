package com.se.sos.domain.admin.controller;

import com.se.sos.domain.admin.service.AdminService;
import com.se.sos.domain.user.entity.Role;
import com.se.sos.global.response.error.ErrorRes;
import com.se.sos.global.response.error.ErrorType;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    @GetMapping("/registration")
    public ResponseEntity<?> getRegistration(
            @RequestParam(name = "role", required = false) Role role,
            @RequestParam(name = "id", required = false) UUID id
    ){
        if (role == null && id == null)
            return adminService.getAllRegistration();
        else if(role != null && id != null)
            return adminService.getRegistration(role, id);
        else
            return ResponseEntity.status(ErrorType.BAD_REQUEST.getStatus())
                    .body(ErrorRes.from(ErrorType.BAD_REQUEST));
    }

}
