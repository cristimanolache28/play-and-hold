package com.playandhold.portfolio_service.brokerage;

import com.playandhold.portfolio_service.brokerage.dto.BrokerageAccountResponse;
import com.playandhold.portfolio_service.brokerage.dto.CreateBrokerageAccountRequest;
import com.playandhold.portfolio_service.brokerage.dto.UpdateBrokerageAccountRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/portfolios/{portfolioId}/brokerage-accounts")
@RequiredArgsConstructor
public class BrokerageAccountController {

    private final BrokerageAccountService brokerageAccountService;

    @PostMapping
    public ResponseEntity<BrokerageAccountResponse> createBrokerageAccount(
            @PathVariable UUID portfolioId,
            @Valid @RequestBody CreateBrokerageAccountRequest request
    ) {
        BrokerageAccountResponse response =
                brokerageAccountService.createBrokerageAccount(
                        portfolioId,
                        request
                );

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @GetMapping("/{brokerageAccountId}")
    public ResponseEntity<BrokerageAccountResponse> getBrokerageAccount(@PathVariable UUID portfolioId, @PathVariable UUID brokerageAccountId) {
        BrokerageAccountResponse response =
                brokerageAccountService.getBrokerageAccount(
                        portfolioId,
                        brokerageAccountId
                );

        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<BrokerageAccountResponse>> getAllBrokerageAccounts(@PathVariable UUID portfolioId) {
        List<BrokerageAccountResponse> response =
                brokerageAccountService
                        .getAllBrokerageAccounts(portfolioId);

        return ResponseEntity.ok(response);
    }

    @PutMapping("/{brokerageAccountId}")
    public ResponseEntity<BrokerageAccountResponse> updateBrokerageAccount(
            @PathVariable UUID portfolioId,
            @PathVariable UUID brokerageAccountId,
            @Valid @RequestBody UpdateBrokerageAccountRequest request
    ) {

        BrokerageAccountResponse response =
                brokerageAccountService.updateBrokerageAccount(
                        portfolioId,
                        brokerageAccountId,
                        request
                );

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{brokerageAccountId}")
    public ResponseEntity<Void> deleteBrokerageAccount(
            @PathVariable UUID portfolioId,
            @PathVariable UUID brokerageAccountId
    ) {

        brokerageAccountService.deleteBrokerageAccount(
                portfolioId,
                brokerageAccountId
        );

        return ResponseEntity.noContent().build();
    }
}
