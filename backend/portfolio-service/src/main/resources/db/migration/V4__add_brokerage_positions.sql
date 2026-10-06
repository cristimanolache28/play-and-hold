CREATE TABLE brokerage_positions (
    brokerage_position_id UUID PRIMARY KEY,

    portfolio_id UUID NOT NULL,
    tradable_asset_id UUID NOT NULL,
    brokerage_account_id UUID NOT NULL,

    quantity NUMERIC(19, 8) NOT NULL,
    average_buy_price NUMERIC(19, 8) NOT NULL,

    CONSTRAINT fk_brokerage_positions_portfolio
        FOREIGN KEY (portfolio_id)
        REFERENCES portfolios(portfolio_id)
        ON DELETE CASCADE,

    CONSTRAINT fk_brokerage_positions_asset
        FOREIGN KEY (tradable_asset_id)
        REFERENCES tradable_assets(tradable_asset_id),

    CONSTRAINT fk_brokerage_positions_account
        FOREIGN KEY (brokerage_account_id)
        REFERENCES brokerage_accounts(brokerage_account_id)
        ON DELETE CASCADE,

    CONSTRAINT uk_brokerage_position
            portfolio_id,
            tradable_asset_id,
            brokerage_account_id
        ),

    CONSTRAINT ck_brokerage_position_quantity
        CHECK (quantity > 0),

    CONSTRAINT ck_brokerage_position_average_price
        CHECK (average_buy_price > 0)
);