package com.playandhold.portfolio_service.brokerageposition;

import com.playandhold.portfolio_service.transaction.PortfolioTransaction;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
@RequiredArgsConstructor
public class BrokeragePositionService {

    private final BrokeragePositionRepository brokeragePositionRepository;

    @Transactional
    public BrokeragePosition applyBuy(
            PortfolioTransaction transaction
    ) {
        return brokeragePositionRepository
                .findByPortfolioIdAndTradableAssetIdAndBrokerageAccountId(
                        transaction.getPortfolioId(),
                        transaction.getTradableAssetId(),
                        transaction.getBrokerageAccountId()
                )
                .map(existingPosition ->
                        updateExistingPosition(
                                existingPosition,
                                transaction
                        )
                )
                .orElseGet(() ->
                        createPosition(transaction)
                );
    }

    private BrokeragePosition createPosition(
            PortfolioTransaction transaction
    ) {
        BrokeragePosition position =
                BrokeragePosition.builder()
                        .portfolioId(transaction.getPortfolioId())
                        .tradableAssetId(transaction.getTradableAssetId())
                        .brokerageAccountId(transaction.getBrokerageAccountId())
                        .quantity(transaction.getQuantity())
                        .averageBuyPrice(transaction.getPrice())
                        .build();

        return brokeragePositionRepository.save(position);
    }

    private BrokeragePosition updateExistingPosition(
            BrokeragePosition position,
            PortfolioTransaction transaction
    ) {
        BigDecimal existingCost =
                position.getQuantity()
                        .multiply(position.getAverageBuyPrice());

        BigDecimal newBuyCost =
                transaction.getQuantity()
                        .multiply(transaction.getPrice());

        BigDecimal newQuantity =
                position.getQuantity()
                        .add(transaction.getQuantity());

        BigDecimal newAverageBuyPrice =
                existingCost
                        .add(newBuyCost)
                        .divide(
                                newQuantity,
                                8,
                                RoundingMode.HALF_UP
                        );

        position.setQuantity(newQuantity);
        position.setAverageBuyPrice(newAverageBuyPrice);

        return brokeragePositionRepository.save(position);
    }
}