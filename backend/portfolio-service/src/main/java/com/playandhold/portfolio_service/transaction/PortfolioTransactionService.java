package com.playandhold.portfolio_service.transaction;

import com.playandhold.portfolio_service.asset.TradableAsset;
import com.playandhold.portfolio_service.asset.TradableAssetRepository;
import com.playandhold.portfolio_service.brokerage.BrokerageAccountRepository;
import com.playandhold.portfolio_service.positionlot.PositionLotService;
import com.playandhold.portfolio_service.transaction.dto.CreatePortfolioTransactionRequest;
import com.playandhold.portfolio_service.transaction.dto.PortfolioTransactionResponse;
import com.playandhold.portfolio_service.transaction.dto.UpdatePortfolioTransactionRequest;
import com.playandhold.portfolio_service.transaction.exception.PortfolioTransactionNotFoundException;
import com.playandhold.portfolio_service.transaction.exception.TradableAssetNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PortfolioTransactionService {

    private final PortfolioTransactionRepository transactionRepository;
    private final BrokerageAccountRepository brokerageAccountRepository;
    private final PortfolioTransactionMapper mapper;
    private final TradableAssetRepository tradableAssetRepository;
    private final PositionLotService positionLotService;

    @Transactional
    public PortfolioTransactionResponse createTransaction(UUID portfolioId, CreatePortfolioTransactionRequest request) {
        TradableAsset tradableAsset =
                tradableAssetRepository
                        .findBySymbolIgnoreCase(request.symbol())
                        .orElseThrow(
                                () -> new TradableAssetNotFoundException(request.symbol())
                        );

        validateBrokerageAccount(portfolioId, request.brokerageAccountId());

        PortfolioTransaction transaction =
                mapper.toEntity(
                        portfolioId,
                        tradableAsset.getId(),
                        request
                );

        PortfolioTransaction savedTransaction = transactionRepository.save(transaction);

        if (savedTransaction.getTransactionType() == TransactionType.BUY) {
            positionLotService.createLot(savedTransaction);
        }

        return mapper.toResponse(savedTransaction);
    }

    @Transactional(readOnly = true)
    public PortfolioTransactionResponse getTransaction(UUID portfolioId, UUID transactionId
    ) {
        PortfolioTransaction transaction = findTransaction(portfolioId, transactionId);

        return mapper.toResponse(transaction);
    }

    @Transactional(readOnly = true)
    public List<PortfolioTransactionResponse> getAllTransactions(UUID portfolioId) {
        return transactionRepository
                .findAllByPortfolioId(portfolioId)
                .stream()
                .map(mapper::toResponse)
                .toList();
    }

    @Transactional
    public PortfolioTransactionResponse updateTransaction(UUID portfolioId, UUID transactionId, UpdatePortfolioTransactionRequest request) {
        PortfolioTransaction transaction = findTransaction(portfolioId, transactionId);

        validateBrokerageAccount(portfolioId, request.brokerageAccountId());

        mapper.updateEntity(transaction, request);

        PortfolioTransaction updatedTransaction = transactionRepository.save(transaction);

        return mapper.toResponse(updatedTransaction);
    }

    @Transactional
    public void deleteTransaction(UUID portfolioId, UUID transactionId) {
        PortfolioTransaction transaction =
                findTransaction(
                        portfolioId,
                        transactionId
                );

        transactionRepository.delete(transaction);
    }

    private PortfolioTransaction findTransaction(UUID portfolioId, UUID transactionId) {
        return transactionRepository.findByIdAndPortfolioId(transactionId, portfolioId)
                .orElseThrow(
                        () -> new PortfolioTransactionNotFoundException(
                                portfolioId,
                                transactionId
                        )
                );
    }

    private void validateBrokerageAccount(UUID portfolioId, UUID brokerageAccountId
    ) {
        brokerageAccountRepository.findByIdAndPortfolioId(brokerageAccountId, portfolioId)
                .orElseThrow(
                        () -> new IllegalArgumentException("Brokerage account does not belong to this portfolio")
                );
    }

    private void validateTradableAsset(UUID tradableAssetId) {
        if (!tradableAssetRepository.existsById(tradableAssetId)) {
            throw new IllegalArgumentException("Tradable asset not found with id: " + tradableAssetId);
        }
    }
}