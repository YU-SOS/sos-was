package com.se.sos;

import com.se.sos.global.exception.CustomException;
import com.se.sos.global.response.error.ErrorType;
import com.se.sos.global.response.success.SuccessType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

    @GetMapping("/test/{val}")
//    public ResponseEntity<?> testPermitAll(@AuthenticationPrincipal UserDetails user){
        public ResponseEntity<?> testPermitAll(@PathVariable("val") String val){

        System.out.println(val);
        if(val.equals("error")){
            throw new NullPointerException("test null pointer error");
        } else if(val.equals("error2")){
            throw new CustomException(ErrorType.USER_NOT_FOUND);
        }

        return ResponseEntity.status(SuccessType.OK.getStatus()).
                body(SuccessType.OK.getStatusCode() + SuccessType.OK.getMessage());
    }

    @GetMapping("/test/amb")
    public String test2(){
        return "test amb auth";
    }
}
