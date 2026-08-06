package com.playandhold.portfolio_service.portofolio.exception;

import java.util.UUID;

public class PortfolioNotFoundException extends RuntimeException {
    public PortfolioNotFoundException(UUID portfolioId) {
        super("Portfolio not found: " + portfolioId);
    }
}