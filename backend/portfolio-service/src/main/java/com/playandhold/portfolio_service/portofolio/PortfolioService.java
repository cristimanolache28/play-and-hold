package com.playandhold.portfolio_service.portofolio;

import com.playandhold.portfolio_service.portofolio.dto.CreatePortfolioRequest;
import com.playandhold.portfolio_service.portofolio.dto.PortfolioResponse;
import com.playandhold.portfolio_service.portofolio.dto.UpdatePortfolioRequest;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PortfolioService {

    private final PortfolioRepository portfolioRepository;
    private final PortfolioMapper portfolioMapper;

    @Transactional
    public PortfolioResponse createPortfolio(UUID userId, CreatePortfolioRequest request) {
        Portfolio portfolio = portfolioMapper.toEntity(userId, request);
        Portfolio savedPortfolio = portfolioRepository.save(portfolio);

        return portfolioMapper.toDto(savedPortfolio);
    }

    @Transactional(readOnly = true)
    public PortfolioResponse getPortfolio(UUID userId, UUID portfolioId) {
        Portfolio portfolio = portfolioRepository
                .findByUserIdAndPortfolioId(userId, portfolioId)
                .orElseThrow(() -> new IllegalArgumentException("Portfolio not found: " + portfolioId));

        return portfolioMapper.toDto(portfolio);
    }

    public PortfolioResponse updatePortfolio(UUID userId, UUID portfolioId, UpdatePortfolioRequest request) {
        Portfolio portfolio = portfolioRepository.findByUserIdAndPortfolioId(userId, portfolioId)
                        .orElseThrow(() -> new IllegalArgumentException("Portfolio not found: " +portfolioId));

        portfolioMapper.updatePortfolioDto(portfolio, request);
        Portfolio savedPortfolio = portfolioRepository.save(portfolio);
        return portfolioMapper.toDto(savedPortfolio);
    }

    public String deletePortfolio(UUID userId, UUID portfolioId) {
        Portfolio portfolio = portfolioRepository.findByUserIdAndPortfolioId(userId, portfolioId)
                .orElseThrow(() -> new IllegalArgumentException("Portfolio not found: " +portfolioId));

        portfolioRepository.delete(portfolio);

        return "Portfolio " + portfolioId + " was deleted with successfully.";
    }

}
