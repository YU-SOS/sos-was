package com.se.sos.domain.auth.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;

@Getter
public class SignupReq {

    @NotBlank
    private String id;
    @NotBlank
    private String password;
    @NotBlank
    private String name;
    @NotBlank
    private String address;
    @NotBlank
    private String telephoneNumber;

    public SignupReq(){
    }

    @Builder
    public SignupReq(String id, String password, String name, String address, String telephoneNumber ) {
        this.id = id;
        this.password = password;
        this.name = name;
        this.address = address;
        this.telephoneNumber = telephoneNumber;
    }
}