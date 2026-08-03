package com.playandhold.portfolio_service.portofolio.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record CreatePortfolioRequest(
        @NotBlank(message = "Portfolio name is required")
        @Size(max = 100, message = "Portfolio name must not exceed 100 characters")
        String name,

        @NotBlank(message = "Base currency is required")
        @Pattern(
                regexp = "^[A-Z]{3}$",
                message = "Base currency must contain exactly three uppercase letters"
        )
        String baseCurrency
) {

}
