package com.playandhold.portfolio_service.portofolio;

import com.playandhold.portfolio_service.portofolio.dto.CreatePortfolioRequest;
import com.playandhold.portfolio_service.portofolio.dto.PortfolioResponse;
import com.playandhold.portfolio_service.portofolio.dto.UpdatePortfolioRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/portfolios")
@RequiredArgsConstructor
public class PortfolioController {

    private final PortfolioService portfolioService;

    @PostMapping
    public ResponseEntity<PortfolioResponse> createPortfolio(
            @RequestHeader("X-User-Id") UUID userId,
            @Valid @RequestBody CreatePortfolioRequest request
    ) {
        PortfolioResponse response =
                portfolioService.createPortfolio(userId, request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @GetMapping("/{portfolioId}")
    public ResponseEntity<PortfolioResponse> getPortfolio(
            @RequestHeader("X-User-Id") UUID userId,
            @PathVariable UUID portfolioId
    ) {
        return ResponseEntity.ok(
                portfolioService.getPortfolio(userId, portfolioId)
        );
    }

    @PatchMapping("/{portfolioId}")
    public ResponseEntity<PortfolioResponse> updatePortfolio(
            @RequestHeader("X-User-Id") UUID userId,
            @PathVariable UUID portfolioId,
            @Valid @RequestBody UpdatePortfolioRequest request
    ) {
        PortfolioResponse response =
                portfolioService.updatePortfolio(
                        userId,
                        portfolioId,
                        request
                );

        return ResponseEntity.ok(response);
    }
}