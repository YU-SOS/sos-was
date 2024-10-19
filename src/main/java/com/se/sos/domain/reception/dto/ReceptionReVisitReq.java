package com.se.sos.domain.reception.dto;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record ReceptionReVisitReq (
        @NotNull UUID hospitalId
) {
}
