DROP INDEX idx_portfolio_transactions_lookup;

ALTER TABLE portfolio_transactions
    ADD COLUMN fees NUMERIC(19, 8) NOT NULL DEFAULT 0;

ALTER TABLE portfolio_transactions
    ADD COLUMN executed_at TIMESTAMP WITH TIME ZONE NOT NULL;

ALTER TABLE portfolio_transactions
    ADD COLUMN notes VARCHAR(500);

ALTER TABLE portfolio_transactions
    DROP COLUMN transaction_date;

CREATE INDEX idx_portfolio_transactions_lookup
    ON portfolio_transactions(
        portfolio_id,
        tradable_asset_id,
        brokerage_account_id,
        executed_at
    );