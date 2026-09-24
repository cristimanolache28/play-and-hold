package com.playandhold.portfolio_service.brokerage;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "brokerage_accounts")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BrokerageAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "brokerage_account_id", nullable = false, updatable = false)
    private UUID id;

    @Column(name = "portfolio_id", nullable = false)
    private UUID portfolioId;

    @Column(name = "broker_name", nullable = false, length = 100)
    private String brokerName;

    @Column(name = "account_name", length = 100)
    private String accountName;

    @Column(nullable = false, length = 3)
    private String currency;

    @Enumerated(EnumType.STRING)
    @Column(name = "sell_mode", nullable = false, length = 20)
    private SellMode sellMode;

    @Enumerated(EnumType.STRING)
    @Column(name = "lot_allocation_method", length = 10)
    private LotAllocationMethod lotAllocationMethod;

    @Column(nullable = false)
    private boolean active;
}