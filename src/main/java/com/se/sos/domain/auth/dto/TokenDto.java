package com.se.sos.domain.auth.dto;

import lombok.Builder;

@Builder
public record TokenDto(
        String accessToken,
        String refreshToken
) {
}
