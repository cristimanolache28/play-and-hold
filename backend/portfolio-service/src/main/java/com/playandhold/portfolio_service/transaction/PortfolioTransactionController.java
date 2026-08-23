package com.playandhold.portfolio_service.transaction;

import com.playandhold.portfolio_service.transaction.dto.CreatePortfolioTransactionRequest;
import com.playandhold.portfolio_service.transaction.dto.PortfolioTransactionResponse;
import com.playandhold.portfolio_service.transaction.dto.UpdatePortfolioTransactionRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/portfolios/{portfolioId}/transactions")
@RequiredArgsConstructor
public class PortfolioTransactionController {

    private final PortfolioTransactionService transactionService;

    @PostMapping
    public ResponseEntity<PortfolioTransactionResponse> createTransaction(
            @PathVariable UUID portfolioId,
            @Valid @RequestBody CreatePortfolioTransactionRequest request
    ) {

        PortfolioTransactionResponse response = transactionService.createTransaction(portfolioId, request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @GetMapping("/{transactionId}")
    public ResponseEntity<PortfolioTransactionResponse> getTransaction(@PathVariable UUID portfolioId, @PathVariable UUID transactionId
    ) {
        return ResponseEntity.ok(
                transactionService.getTransaction(portfolioId, transactionId)
        );
    }

    @GetMapping
    public ResponseEntity<List<PortfolioTransactionResponse>> getAllTransactions(
            @PathVariable UUID portfolioId
    ) {

        return ResponseEntity.ok(transactionService.getAllTransactions(portfolioId)
        );
    }

    @PutMapping("/{transactionId}")
    public ResponseEntity<PortfolioTransactionResponse> updateTransaction(
            @PathVariable UUID portfolioId,
            @PathVariable UUID transactionId,
            @Valid @RequestBody UpdatePortfolioTransactionRequest request
    ) {

        return ResponseEntity.ok(
                transactionService.updateTransaction(portfolioId, transactionId, request)
        );
    }

    @DeleteMapping("/{transactionId}")
    public ResponseEntity<Void> deleteTransaction(@PathVariable UUID portfolioId, @PathVariable UUID transactionId) {
        transactionService.deleteTransaction(portfolioId, transactionId);

        return ResponseEntity.noContent().build();
    }
}