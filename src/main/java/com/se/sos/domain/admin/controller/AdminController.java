package com.se.sos.domain.admin.controller;

import com.se.sos.domain.admin.service.AdminService;
import com.se.sos.domain.user.entity.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    @GetMapping("/registration")
    public ResponseEntity<?> getAllRegistration(){
        return adminService.getAllRegistration();
    }

    @PostMapping("/registration")
    public ResponseEntity<?> addRegistration(@RequestParam(name = "role") Role role, @RequestParam(name = "id") UUID id){
        return adminService.getRegistration(role, id);
    }
}
