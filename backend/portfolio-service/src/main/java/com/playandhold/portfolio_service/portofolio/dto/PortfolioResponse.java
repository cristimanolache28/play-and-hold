package com.playandhold.portfolio_service.portofolio.dto;

import com.playandhold.portfolio_service.portofolio.PortfolioStatus;

import java.time.Instant;
import java.util.UUID;

public record PortfolioResponse(
        UUID id,
        UUID userId,
        String name,
        String baseCurrency,
        PortfolioStatus status,
        Instant createdAt,
        Instant updatedAt
) {
}
