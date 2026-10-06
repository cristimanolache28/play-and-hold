package com.playandhold.portfolio_service.brokerage;

import com.playandhold.portfolio_service.brokerage.dto.BrokerageAccountResponse;
import com.playandhold.portfolio_service.brokerage.dto.CreateBrokerageAccountRequest;
import com.playandhold.portfolio_service.brokerage.dto.UpdateBrokerageAccountRequest;
import com.playandhold.portfolio_service.brokerage.exception.BrokerageAccountNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BrokerageAccountService {

    private final BrokerageAccountRepository repository;
    private final BrokerageAccountMapper mapper;

    @Transactional
    public BrokerageAccountResponse createBrokerageAccount(UUID portfolioId, CreateBrokerageAccountRequest request) {
        BrokerageAccount brokerageAccount =
                mapper.toEntity(portfolioId, request);

        BrokerageAccount savedBrokerageAccount =
                repository.save(brokerageAccount);

        return mapper.toDto(savedBrokerageAccount);
    }

    @Transactional(readOnly = true)
    public BrokerageAccountResponse getBrokerageAccount(UUID portfolioId, UUID brokerageAccountId) {
        BrokerageAccount brokerageAccount =
                findBrokerageAccount(
                        portfolioId,
                        brokerageAccountId
                );

        return mapper.toDto(brokerageAccount);
    }

    @Transactional(readOnly = true)
    public List<BrokerageAccountResponse> getAllBrokerageAccounts(UUID portfolioId) {
        return repository.findAllByPortfolioId(portfolioId)
                .stream()
                .map(mapper::toDto)
                .toList();
    }

    @Transactional
    public BrokerageAccountResponse updateBrokerageAccount(UUID portfolioId, UUID brokerageAccountId, UpdateBrokerageAccountRequest request) {
        BrokerageAccount brokerageAccount =
                findBrokerageAccount(portfolioId, brokerageAccountId);

        mapper.updateEntity(brokerageAccount, request);

        BrokerageAccount updatedBrokerageAccount = repository.save(brokerageAccount);

        return mapper.toDto(updatedBrokerageAccount);
    }

    @Transactional
    public void deleteBrokerageAccount(UUID portfolioId, UUID brokerageAccountId) {
        BrokerageAccount brokerageAccount =
                findBrokerageAccount(portfolioId, brokerageAccountId);

        repository.delete(brokerageAccount);
    }

    private BrokerageAccount findBrokerageAccount(UUID portfolioId, UUID brokerageAccountId
    ) {
        return repository
                .findByIdAndPortfolioId(brokerageAccountId, portfolioId)
                .orElseThrow(
                        () -> new BrokerageAccountNotFoundException(portfolioId, brokerageAccountId)
                );
    }

    private void validateSellBehavior(
            SellMode sellMode,
            LotAllocationMethod lotAllocationMethod
    ) {
        if (sellMode == SellMode.POSITION_BASED
                && lotAllocationMethod != null) {
            throw new IllegalArgumentException(
                    "POSITION_BASED accounts must not define a lot allocation method"
            );
        }

        if (sellMode == SellMode.LOT_BASED
                && lotAllocationMethod == null) {
            throw new IllegalArgumentException(
                    "LOT_BASED accounts must define FIFO or LIFO allocation"
            );
        }
    }
}
