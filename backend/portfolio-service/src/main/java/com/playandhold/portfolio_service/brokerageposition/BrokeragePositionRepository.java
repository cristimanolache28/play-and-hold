package com.playandhold.portfolio_service.brokerageposition;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface BrokeragePositionRepository
        extends JpaRepository<BrokeragePosition, UUID> {

    Optional<BrokeragePosition> findByPortfolioIdAndTradableAssetIdAndBrokerageAccountId(
            UUID portfolioId,
            UUID tradableAssetId,
            UUID brokerageAccountId
    );
}