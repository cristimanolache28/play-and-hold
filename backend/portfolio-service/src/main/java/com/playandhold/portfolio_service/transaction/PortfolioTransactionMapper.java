package com.playandhold.portfolio_service.transaction;

import com.playandhold.portfolio_service.transaction.dto.CreatePortfolioTransactionRequest;
import com.playandhold.portfolio_service.transaction.dto.PortfolioTransactionResponse;
import com.playandhold.portfolio_service.transaction.dto.UpdatePortfolioTransactionRequest;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class PortfolioTransactionMapper {

    public PortfolioTransaction toEntity(
            UUID portfolioId,
            UUID tradableAssetId,
            CreatePortfolioTransactionRequest request) {
        return PortfolioTransaction.builder()
                .portfolioId(portfolioId)
                .tradableAssetId(tradableAssetId)
                .brokerageAccountId(request.brokerageAccountId())
                .transactionType(request.transactionType())
                .quantity(request.quantity())
                .price(request.price())
                .transactionDate(request.transactionDate())
                .build();
    }

    public PortfolioTransactionResponse toDto(
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
                transaction.getTransactionDate()
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
        transaction.setTransactionDate(request.transactionDate());
    }
}