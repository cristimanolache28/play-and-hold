package com.playandhold.portfolio_service.portofolio.dto;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record UpdatePortfolioRequest(

        @Size(max = 100, message = "Portfolio name must not exceed 100 characters")
        @Pattern(regexp = ".*\\S.*", message = "Portfolio name must not be blank"        )
        String name,

        @Pattern(regexp = "^[A-Z]{3}$", message = "Base currency must contain exactly three uppercase letters"        )
        String baseCurrency

) {
}
