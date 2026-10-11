package com.playandhold.portfolio_service.positionlot;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


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

    @Query("""
        SELECT lot
        FROM PositionLot lot
        JOIN PortfolioTransaction transaction
            ON transaction.id = lot.buyTransactionId
        WHERE lot.portfolioId = :portfolioId
          AND lot.tradableAssetId = :tradableAssetId
          AND lot.brokerageAccountId = :brokerageAccountId
          AND lot.status = :status
        ORDER BY transaction.executedAt ASC, transaction.id ASC
        """)
    List<PositionLot> findLotsOrderedByBuyExecutedAtAsc(
            @Param("portfolioId") UUID portfolioId,
            @Param("tradableAssetId") UUID tradableAssetId,
            @Param("brokerageAccountId") UUID brokerageAccountId,
            @Param("status") LotStatus status
    );

    @Query("""
        SELECT lot
        FROM PositionLot lot
        JOIN PortfolioTransaction transaction
            ON transaction.id = lot.buyTransactionId
        WHERE lot.portfolioId = :portfolioId
          AND lot.tradableAssetId = :tradableAssetId
          AND lot.brokerageAccountId = :brokerageAccountId
          AND lot.status = :status
        ORDER BY transaction.executedAt DESC, transaction.id DESC
        """)
    List<PositionLot> findLotsOrderedByBuyExecutedAtDesc(
            @Param("portfolioId") UUID portfolioId,
            @Param("tradableAssetId") UUID tradableAssetId,
            @Param("brokerageAccountId") UUID brokerageAccountId,
            @Param("status") LotStatus status
    );
}