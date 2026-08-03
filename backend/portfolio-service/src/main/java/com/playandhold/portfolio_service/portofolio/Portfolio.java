package com.playandhold.portfolio_service.portofolio;


import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Entity
@Table(name = "portfolios")
public class Portfolio {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(
            name = "portfolio_id",
            nullable = false,
            updatable = false
    )
    private UUID portfolioId;

    @Column(name = "user_id", nullable = false)
    private UUID userId;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(name = "base_currency", nullable = false, length = 3)
    private String baseCurrency;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private PortfolioStatus status;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    @PrePersist
    public void onCreate() {
        Instant instant = Instant.now();

        createdAt = instant;
        updatedAt = instant;

        if (status == null) {
            status = PortfolioStatus.ACTIVE;
        }
    }

    @PreUpdate
    public void atUpdate() {
        updatedAt = Instant.now();
    }


}
