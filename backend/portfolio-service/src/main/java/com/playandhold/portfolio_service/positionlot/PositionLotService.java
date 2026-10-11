package com.playandhold.portfolio_service.positionlot;

import com.playandhold.portfolio_service.transaction.PortfolioTransaction;
import com.playandhold.portfolio_service.transaction.TransactionType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PositionLotService {

    private final PositionLotRepository positionLotRepository;

    @Transactional
    public PositionLot createLot(PortfolioTransaction transaction) {

        if (transaction.getTransactionType() != TransactionType.BUY) {
            throw new IllegalArgumentException(
                    "Position lot can only be created from a BUY transaction"
            );
        }

        PositionLot positionLot = PositionLot.builder()
                .portfolioId(transaction.getPortfolioId())
                .tradableAssetId(transaction.getTradableAssetId())
                .brokerageAccountId(transaction.getBrokerageAccountId())
                .buyTransactionId(transaction.getId())
                .quantity(transaction.getQuantity())
                .status(LotStatus.OPEN)
                .build();

        return positionLotRepository.save(positionLot);
    }

    @Transactional(readOnly = true)
    public List<PositionLot> getOpenLots(UUID portfolioId, UUID tradableAssetId, UUID brokerageAccountId) {
        return positionLotRepository
                .findAllByPortfolioIdAndTradableAssetIdAndBrokerageAccountIdAndStatus(
                        portfolioId,
                        tradableAssetId,
                        brokerageAccountId,
                        LotStatus.OPEN
                );
    }

    @Transactional(readOnly = true)
    public BigDecimal getAvailableQuantity(UUID portfolioId, UUID tradableAssetId, UUID brokerageAccountId) {
        return getOpenLots(portfolioId, tradableAssetId, brokerageAccountId)
                .stream()
                .map(PositionLot::getQuantity)
                .reduce(
                        BigDecimal.ZERO,
                        BigDecimal::add
                );
    }

    @Transactional(readOnly = true)
    public List<PositionLot> getOpenLotsForFifo(
            UUID portfolioId,
            UUID tradableAssetId,
            UUID brokerageAccountId
    ) {
        return positionLotRepository.findLotsOrderedByBuyExecutedAtAsc(
                portfolioId,
                tradableAssetId,
                brokerageAccountId,
                LotStatus.OPEN
        );
    }

    @Transactional
    public PositionLot consumeQuantity(PositionLot lot, BigDecimal quantityToConsume) {
        if (quantityToConsume.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Quantity to consume must be greater than zero");
        }

        if (quantityToConsume.compareTo(lot.getQuantity()) > 0) {
            throw new IllegalArgumentException("Cannot consume more than the available lot quantity");
        }

        BigDecimal remainingQuantity = lot.getQuantity().subtract(quantityToConsume);
        lot.setQuantity(remainingQuantity);

        if (remainingQuantity.compareTo(BigDecimal.ZERO) == 0) {
            lot.setStatus(LotStatus.CLOSED);
        }

        return positionLotRepository.save(lot);
    }
}