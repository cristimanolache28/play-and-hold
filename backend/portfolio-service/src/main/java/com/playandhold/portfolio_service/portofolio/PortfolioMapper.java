package com.playandhold.portfolio_service.portofolio;

import com.playandhold.portfolio_service.portofolio.dto.CreatePortfolioRequest;
import com.playandhold.portfolio_service.portofolio.dto.PortfolioResponse;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class PortfolioMapper {

    // Create portfolio
    public Portfolio toEntity(UUID userId, CreatePortfolioRequest request) {
        Portfolio portfolio = new Portfolio();
        portfolio.setUserId(userId);
        portfolio.setName(request.name().trim());
        portfolio.setBaseCurrency(request.baseCurrency().toUpperCase());
        portfolio.setStatus(PortfolioStatus.ACTIVE);

        return portfolio;
    }

    public PortfolioResponse toDto(Portfolio portfolio) {
        return new PortfolioResponse(
                portfolio.getPortfolioId(),
                portfolio.getUserId(),
                portfolio.getName(),
                portfolio.getBaseCurrency(),
                portfolio.getStatus(),
                portfolio.getCreatedAt(),
                portfolio.getUpdatedAt()
        );
    }
}
