CREATE TABLE sell_allocations (
    sell_allocation_id UUID PRIMARY KEY,

    sell_transaction_id UUID NOT NULL,
    position_lot_id UUID NOT NULL,

    quantity NUMERIC(19, 8) NOT NULL,

    CONSTRAINT fk_sell_allocations_sell_transaction
        FOREIGN KEY (sell_transaction_id)
        REFERENCES portfolio_transactions(portfolio_transaction_id)
        ON DELETE CASCADE,

    CONSTRAINT fk_sell_allocations_position_lot
        FOREIGN KEY (position_lot_id)
        REFERENCES position_lots(position_lot_id),

    CONSTRAINT uk_sell_allocation_transaction_lot
        UNIQUE (
            sell_transaction_id,
            position_lot_id
        ),

    CONSTRAINT ck_sell_allocation_quantity
        CHECK (quantity > 0)
);

CREATE INDEX idx_sell_allocations_sell_transaction
    ON sell_allocations(sell_transaction_id);

CREATE INDEX idx_sell_allocations_position_lot
    ON sell_allocations(position_lot_id);