package com.se.sos.domain.comment.dto;

import jakarta.validation.constraints.NotBlank;

public record CommentReq(
        @NotBlank String description
) {
}
