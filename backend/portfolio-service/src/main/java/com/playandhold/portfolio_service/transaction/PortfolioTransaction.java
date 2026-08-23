package com.playandhold.portfolio_service.transaction;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "portfolio_transactions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PortfolioTransaction {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "portfolio_transaction_id", nullable = false, updatable = false)
    private UUID id;

    @Column(name = "portfolio_id", nullable = false)
    private UUID portfolioId;

    @Column(name = "tradable_asset_id", nullable = false)
    private UUID tradableAssetId;

    @Column(name = "brokerage_account_id", nullable = false)
    private UUID brokerageAccountId;

    @Enumerated(EnumType.STRING)
    @Column(name = "transaction_type", nullable = false, length = 10)
    private TransactionType transactionType;

    @Column(nullable = false, precision = 19, scale = 8)
    private BigDecimal quantity;

    @Column(nullable = false, precision = 19, scale = 8)
    private BigDecimal price;

    @Column(name = "transaction_date", nullable = false)
    private LocalDate transactionDate;
}
