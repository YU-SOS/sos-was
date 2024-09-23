package com.se.sos;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping("/")
    public String test(@AuthenticationPrincipal UserDetails user){
        System.out.println(user);
        System.out.println(user.getUsername());
        return "test controller";
    }

    @GetMapping("/test")
    public String testPermitAll(@AuthenticationPrincipal UserDetails user){
        System.out.println(user);
        System.out.println(user.getUsername());
        return "test controller2";
    }

    @GetMapping("/test/amb")
    public String test2(){
        return "test amb auth";
    }
}
