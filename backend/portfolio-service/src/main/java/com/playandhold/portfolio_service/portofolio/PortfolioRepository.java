package com.playandhold.portfolio_service.portofolio;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PortfolioRepository extends JpaRepository<Portfolio, UUID> {

    Optional<Portfolio> findByUserIdAndPortfolioId(
            UUID portfolioId,
            UUID userId
    );

    List<Portfolio> findAllByUserIdAndStatus(
            UUID userId,
            PortfolioStatus status
    );
}

