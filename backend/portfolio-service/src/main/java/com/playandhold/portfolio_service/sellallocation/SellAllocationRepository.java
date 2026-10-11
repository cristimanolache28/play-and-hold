package com.playandhold.portfolio_service.sellallocation;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface SellAllocationRepository
        extends JpaRepository<SellAllocation, UUID> {

    List<SellAllocation> findAllBySellTransactionId(
            UUID sellTransactionId
    );

    boolean existsByPositionLotId(
            UUID positionLotId
    );
}
