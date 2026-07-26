package com.playandhold.user_service.dto;


import com.playandhold.user_service.entity.Gender;

import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

public record UserResponse(
        UUID id,
        String firstName,
        String middleName,
        String lastName,
        String username,
        String email,
        Gender gender,
        String country,
        String city,
        LocalDate dateOfBirth,
        Instant registeredAt
) {
}