package com.se.sos.domain.auth.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;

@Getter
public class SignupReq {

    @NotBlank
    private final String id;
    @NotBlank
    private final String password;
    @NotBlank
    private final String name;
    @NotBlank
    private final String address;
    @NotBlank
    private final String telephoneNumber;

    @Builder
    public SignupReq(String id, String password, String name, String address, String telephoneNumber ) {
        this.id = id;
        this.password = password;
        this.name = name;
        this.address = address;
        this.telephoneNumber = telephoneNumber;
    }
}