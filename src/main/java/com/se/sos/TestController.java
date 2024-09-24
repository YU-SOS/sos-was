package com.se.sos;

import com.se.sos.global.response.success.SuccessType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping("/")
    public ResponseEntity<?> test(@AuthenticationPrincipal UserDetails user){
        System.out.println(user);
        System.out.println(user.getUsername());
        return ResponseEntity.status(SuccessType.OK.getStatus()).
                body(SuccessType.OK);
    }

    @GetMapping("/test")
    public ResponseEntity<?> testPermitAll(@AuthenticationPrincipal UserDetails user){
        if(user != null){
            System.out.println(user);
            System.out.println(user.getUsername());
        }
        return ResponseEntity.status(SuccessType.OK.getStatus()).
                body(SuccessType.OK.getStatusCode() + SuccessType.OK.getMessage());
    }

    @GetMapping("/test/amb")
    public String test2(){
        return "test amb auth";
    }
}
