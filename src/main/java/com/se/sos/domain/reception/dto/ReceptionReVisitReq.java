package com.se.sos.domain.reception.dto;

import jakarta.validation.constraints.NotBlank;

import java.util.UUID;

public record ReceptionReVisitReq (
        @NotBlank UUID hospitalId
) {
}
