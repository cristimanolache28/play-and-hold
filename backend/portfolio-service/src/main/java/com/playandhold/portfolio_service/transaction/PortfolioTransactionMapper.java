package com.playandhold.portfolio_service.transaction;

import com.playandhold.portfolio_service.transaction.dto.CreatePortfolioTransactionRequest;
import com.playandhold.portfolio_service.transaction.dto.PortfolioTransactionResponse;
import com.playandhold.portfolio_service.transaction.dto.UpdatePortfolioTransactionRequest;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Component
public class PortfolioTransactionMapper {

    public PortfolioTransaction toEntity(
            UUID portfolioId,
            UUID tradableAssetId,
            CreatePortfolioTransactionRequest request
    ) {
        return PortfolioTransaction.builder()
                .portfolioId(portfolioId)
                .tradableAssetId(tradableAssetId)
                .brokerageAccountId(request.brokerageAccountId())
                .transactionType(request.transactionType())
                .quantity(request.quantity())
                .price(request.price())
                .fees(
                        request.fees() != null
                                ? request.fees()
                                : BigDecimal.ZERO
                )
                .executedAt(
                        request.executedAt() != null
                                ? request.executedAt()
                                : Instant.now()
                )
                .notes(request.notes())
                .build();
    }

    public PortfolioTransactionResponse toResponse(
            PortfolioTransaction transaction
    ) {

        return new PortfolioTransactionResponse(
                transaction.getId(),
                transaction.getPortfolioId(),
                transaction.getTradableAssetId(),
                transaction.getBrokerageAccountId(),
                transaction.getTransactionType(),
                transaction.getQuantity(),
                transaction.getPrice(),
                transaction.getFees(),
                transaction.getExecutedAt(),
                transaction.getNotes()

        );
    }

    public void updateEntity(
            PortfolioTransaction transaction,
            UpdatePortfolioTransactionRequest request
    ) {
        transaction.setTradableAssetId(request.tradableAssetId());
        transaction.setBrokerageAccountId(request.brokerageAccountId());
        transaction.setTransactionType(request.transactionType());
        transaction.setQuantity(request.quantity());
        transaction.setPrice(request.price());
        transaction.setFees(request.fees());
        transaction.setExecutedAt(request.executedAt());
        transaction.setNotes(request.notes());
    }
}