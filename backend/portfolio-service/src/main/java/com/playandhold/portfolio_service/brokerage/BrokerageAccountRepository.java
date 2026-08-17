package com.playandhold.portfolio_service.brokerage;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BrokerageAccountRepository
        extends JpaRepository<BrokerageAccount, UUID> {

    List<BrokerageAccount> findAllByPortfolioId(UUID portfolioId);

    Optional<BrokerageAccount> findByIdAndPortfolioId(UUID id, UUID portfolioId);
}
