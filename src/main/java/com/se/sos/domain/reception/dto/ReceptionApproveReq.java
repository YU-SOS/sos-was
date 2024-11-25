package com.se.sos.domain.reception.dto;


import jakarta.validation.constraints.NotBlank;

public record ReceptionApproveReq(
        boolean isApproved
){

}
