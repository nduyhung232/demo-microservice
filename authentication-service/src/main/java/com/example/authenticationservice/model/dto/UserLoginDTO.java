package com.example.authenticationservice.model.dto;

import jakarta.validation.constraints.NotBlank;

public record UserLoginDTO(
        @NotBlank
        String username,

        @NotBlank
        String password
) {
}