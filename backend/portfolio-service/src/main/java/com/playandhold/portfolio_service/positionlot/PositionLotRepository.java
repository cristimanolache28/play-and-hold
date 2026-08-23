package com.playandhold.portfolio_service.positionlot;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface PositionLotRepository
        extends JpaRepository<PositionLot, UUID> {

    List<PositionLot> findAllByPortfolioIdAndTradableAssetIdAndBrokerageAccountIdAndStatus(
            UUID portfolioId,
            UUID tradableAssetId,
            UUID brokerageAccountId,
            LotStatus status
    );

    List<PositionLot> findAllByPortfolioIdAndTradableAssetIdAndStatus(
            UUID portfolioId,
            UUID tradableAssetId,
            LotStatus status
    );
}