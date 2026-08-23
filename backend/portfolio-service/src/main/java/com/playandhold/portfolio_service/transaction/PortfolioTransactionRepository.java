package com.playandhold.portfolio_service.transaction;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PortfolioTransactionRepository
        extends JpaRepository<PortfolioTransaction, UUID> {

    List<PortfolioTransaction> findAllByPortfolioId(UUID portfolioId);

    Optional<PortfolioTransaction> findByIdAndPortfolioId(
            UUID id,
            UUID portfolioId
    );
}