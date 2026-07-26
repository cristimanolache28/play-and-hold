package com.playandhold.user_service.dto;

import com.playandhold.user_service.entity.Gender;
import jakarta.validation.constraints.*;

import java.time.LocalDate;

public record UpdateUserRequest (

        @NotBlank(message = "First name is required")
        @Size(max = 50, message = "First name cannot exceed 50 characters")
        String firstName,

        @Size(max = 50, message = "Middle name cannot exceed 50 characters")
        String middleName,

        @NotBlank(message = "Last name is required")
        @Size(max = 50, message = "Last name cannot exceed 50 characters")
        String lastName,

        @NotBlank(message = "Username is required")
        @Size(
                min = 3,
                max = 30,
                message = "Username must contain between 3 and 30 characters"
        )
        @Pattern(
                regexp = "^[a-zA-Z0-9._-]+$",
                message = "Username contains invalid characters"
        )
        String username,


        @NotNull(message = "Gender is required")
        Gender gender,

        @NotBlank(message = "Country is required")
        @Size(max = 100)
        String country,

        @NotBlank(message = "City is required")
        @Size(max = 100)
        String city,

        @NotNull(message = "Date of birth is required")
        @Past(message = "Date of birth must be in the past")
        LocalDate dateOfBirth

) {

}
