package com.se.sos.domain.admin.dto;

import lombok.Builder;

@Builder
public record SystemStatusRes (
        long hospitalCount,
        long ambulanceCount,
        long userCount,
        long receptionCount
) {
}
