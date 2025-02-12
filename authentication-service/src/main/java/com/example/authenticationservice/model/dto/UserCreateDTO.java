package com.example.authenticationservice.model.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record UserCreateDTO(
        @NotBlank
        String username,

        @NotBlank
        String password,

        @Email(message = "invalid email format")
        String email
) {
}