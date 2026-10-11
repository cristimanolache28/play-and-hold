package com.playandhold.portfolio_service.brokerageposition;

import com.playandhold.portfolio_service.transaction.PortfolioTransaction;
import com.playandhold.portfolio_service.transaction.TransactionType;
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
                        updateExistingPosition(existingPosition, transaction)
                )
                .orElseGet(() -> createPosition(transaction)
                );
    }

    private BrokeragePosition createPosition(PortfolioTransaction transaction) {
        BrokeragePosition position = BrokeragePosition.builder()
                        .portfolioId(transaction.getPortfolioId())
                        .tradableAssetId(transaction.getTradableAssetId())
                        .brokerageAccountId(transaction.getBrokerageAccountId())
                        .quantity(transaction.getQuantity())
                        .averageBuyPrice(transaction.getPrice())
                        .build();

        return brokeragePositionRepository.save(position);
    }

    private BrokeragePosition updateExistingPosition(BrokeragePosition position, PortfolioTransaction transaction) {
        BigDecimal existingCost = position
                .getQuantity()
                .multiply(position.getAverageBuyPrice());

        BigDecimal newBuyCost = transaction
                .getQuantity()
                .multiply(transaction.getPrice());

        BigDecimal newQuantity = position
                .getQuantity()
                .add(transaction.getQuantity());

        BigDecimal newAverageBuyPrice = existingCost
                        .add(newBuyCost)
                        .divide(newQuantity, 8, RoundingMode.HALF_UP);

        position.setQuantity(newQuantity);
        position.setAverageBuyPrice(newAverageBuyPrice);

        return brokeragePositionRepository.save(position);
    }

    @Transactional
    public void applySell(PortfolioTransaction transaction) {
        if (transaction.getTransactionType() != TransactionType.SELL) {
            throw new IllegalArgumentException("Brokerage position sell can only be applied from a SELL transaction");
        }

        BrokeragePosition position = brokeragePositionRepository
                        .findByPortfolioIdAndTradableAssetIdAndBrokerageAccountId(
                                transaction.getPortfolioId(),
                                transaction.getTradableAssetId(),
                                transaction.getBrokerageAccountId()
                        )
                        .orElseThrow(() -> new IllegalArgumentException("No open brokerage position available for sell"));

        if (transaction.getQuantity().compareTo(position.getQuantity()) > 0) {
            throw new IllegalArgumentException("Insufficient quantity available for sell");
        }

        BigDecimal remainingQuantity = position
                .getQuantity()
                .subtract(transaction.getQuantity());

        if (remainingQuantity.compareTo(BigDecimal.ZERO) == 0) {
            brokeragePositionRepository.delete(position);
            return;
        }
        position.setQuantity(remainingQuantity);
        brokeragePositionRepository.save(position);
    }
}