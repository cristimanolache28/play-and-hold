 CREATE TABLE portfolios (
     portfolio_id UUID PRIMARY KEY,
     user_id UUID NOT NULL,
     name VARCHAR(100) NOT NULL,
     base_currency VARCHAR(3) NOT NULL,
     status VARCHAR(20) NOT NULL,
     created_at TIMESTAMP WITH TIME ZONE NOT NULL,
     updated_at TIMESTAMP WITH TIME ZONE NOT NULL
 );


 CREATE TABLE tradable_assets (
     tradable_asset_id UUID PRIMARY KEY,
     symbol VARCHAR(20) NOT NULL,
     display_name VARCHAR(150) NOT NULL,
     exchange_code VARCHAR(20) NOT NULL,
     currency VARCHAR(3) NOT NULL,
     asset_type VARCHAR(20) NOT NULL,
     sector VARCHAR(100),
     industry VARCHAR(100),
     active BOOLEAN NOT NULL,

     CONSTRAINT uk_asset_symbol_exchange
         UNIQUE (symbol, exchange_code)
 );


 CREATE TABLE brokerage_accounts (
     brokerage_account_id UUID PRIMARY KEY,
     portfolio_id UUID NOT NULL,
     broker_name VARCHAR(100) NOT NULL,
     account_name VARCHAR(100),
     currency VARCHAR(3) NOT NULL,

     sell_mode VARCHAR(20) NOT NULL,
     lot_allocation_method VARCHAR(10),

     active BOOLEAN NOT NULL,

     CONSTRAINT fk_brokerage_accounts_portfolio
         FOREIGN KEY (portfolio_id)
         REFERENCES portfolios(portfolio_id)
         ON DELETE CASCADE,

     CONSTRAINT ck_brokerage_sell_behavior
         CHECK (
             (sell_mode = 'POSITION_BASED'
                 AND lot_allocation_method IS NULL)
             OR
             (sell_mode = 'LOT_BASED'
                 AND lot_allocation_method IN ('FIFO', 'LIFO'))
         )
 );


 CREATE TABLE portfolio_transactions (
     portfolio_transaction_id UUID PRIMARY KEY,
     portfolio_id UUID NOT NULL,
     tradable_asset_id UUID NOT NULL,
     brokerage_account_id UUID NOT NULL,
     transaction_type VARCHAR(10) NOT NULL,
     quantity NUMERIC(19, 8) NOT NULL,
     price NUMERIC(19, 8) NOT NULL,
     transaction_date DATE NOT NULL,

     CONSTRAINT fk_transactions_portfolio
         FOREIGN KEY (portfolio_id)
         REFERENCES portfolios(portfolio_id)
         ON DELETE CASCADE,

     CONSTRAINT fk_transactions_asset
         FOREIGN KEY (tradable_asset_id)
         REFERENCES tradable_assets(tradable_asset_id),

     CONSTRAINT fk_transactions_brokerage_account
         FOREIGN KEY (brokerage_account_id)
         REFERENCES brokerage_accounts(brokerage_account_id)
 );


 CREATE TABLE position_lots (
     position_lot_id UUID PRIMARY KEY,
     portfolio_id UUID NOT NULL,
     tradable_asset_id UUID NOT NULL,
     brokerage_account_id UUID NOT NULL,
     buy_transaction_id UUID NOT NULL UNIQUE,
     quantity NUMERIC(19, 8) NOT NULL,
     status VARCHAR(20) NOT NULL,

     CONSTRAINT fk_position_lots_portfolio
         FOREIGN KEY (portfolio_id)
         REFERENCES portfolios(portfolio_id)
         ON DELETE CASCADE,

     CONSTRAINT fk_position_lots_asset
         FOREIGN KEY (tradable_asset_id)
         REFERENCES tradable_assets(tradable_asset_id),

     CONSTRAINT fk_position_lots_brokerage_account
         FOREIGN KEY (brokerage_account_id)
         REFERENCES brokerage_accounts(brokerage_account_id),

     CONSTRAINT fk_position_lots_buy_transaction
         FOREIGN KEY (buy_transaction_id)
         REFERENCES portfolio_transactions(portfolio_transaction_id)
         ON DELETE CASCADE
 );


 CREATE INDEX idx_brokerage_accounts_portfolio
     ON brokerage_accounts(portfolio_id);

 CREATE INDEX idx_portfolio_transactions_lookup
     ON portfolio_transactions(
         portfolio_id,
         tradable_asset_id,
         brokerage_account_id,
         transaction_date
     );

 CREATE INDEX idx_position_lots_open_lookup
     ON position_lots(
         portfolio_id,
         tradable_asset_id,
         brokerage_account_id,
         status
     );