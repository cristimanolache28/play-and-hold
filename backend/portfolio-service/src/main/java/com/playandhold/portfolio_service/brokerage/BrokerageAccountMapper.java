package com.playandhold.portfolio_service.brokerage;

import com.playandhold.portfolio_service.brokerage.dto.BrokerageAccountResponse;
import com.playandhold.portfolio_service.brokerage.dto.CreateBrokerageAccountRequest;
import com.playandhold.portfolio_service.brokerage.dto.UpdateBrokerageAccountRequest;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class BrokerageAccountMapper {

    public BrokerageAccount toEntity(UUID portfolioId, CreateBrokerageAccountRequest request) {
        BrokerageAccount brokerageAccount = new BrokerageAccount();

        brokerageAccount.setPortfolioId(portfolioId);
        brokerageAccount.setBrokerName(request.brokerName());
        brokerageAccount.setAccountName(request.accountName());
        brokerageAccount.setCurrency(request.currency());
        brokerageAccount.setActive(true);
        brokerageAccount.setSellMode(request.sellMode());
        brokerageAccount.setLotAllocationMethod(request.lotAllocationMethod());

        return brokerageAccount;
    }

    public BrokerageAccountResponse toDto(BrokerageAccount brokerageAccount) {
        return new BrokerageAccountResponse(
                brokerageAccount.getId(),
                brokerageAccount.getPortfolioId(),
                brokerageAccount.getBrokerName(),
                brokerageAccount.getAccountName(),
                brokerageAccount.getCurrency(),
                brokerageAccount.getSellMode(),
                brokerageAccount.getLotAllocationMethod(),
                brokerageAccount.isActive()
        );
    }

    public void updateEntity(BrokerageAccount brokerageAccount, UpdateBrokerageAccountRequest request) {
        brokerageAccount.setBrokerName(request.brokerName());
        brokerageAccount.setAccountName(request.accountName());
        brokerageAccount.setCurrency(request.currency());
        brokerageAccount.setActive(request.active());
    }
}
