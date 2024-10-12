package com.se.sos.domain.auth.dto;

import jakarta.validation.constraints.NotBlank;

public record AdminLoginReq(
        @NotBlank(message = "아이디는 필수 입력값 입니다.")
        String adminId,
        @NotBlank(message = "비밀번호는 필수 입력값 입니다.")
        String password
) {

}
