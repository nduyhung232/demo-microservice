package com.example.authenticationservice.model.dto;

import java.time.Instant;

public record UserRespDTO(
        String id,
        String username,
        String password,
        String email,
        Boolean isActive,
        Instant createdAt,
        Instant updatedAt
) {
}
