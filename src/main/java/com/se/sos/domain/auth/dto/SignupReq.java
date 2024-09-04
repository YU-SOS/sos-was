package com.se.sos.domain.auth.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class SignupReq {
    private final String id;
    private final String password;
    private final String name;
    private final String address;
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