# Play & Hold — Backend Overview

## 1. Project Purpose

**Play & Hold** is an investment portfolio tracking application designed to consolidate positions across multiple brokerage platforms.

The main idea is to allow the user to manually enter only the information that is relevant to them:

* financial asset symbol;
* brokerage account;
* transaction type: BUY or SELL;
* quantity;
* price;
* transaction date.

The backend is responsible for resolving and managing the relationships between portfolios, assets, brokerage accounts, transactions, position lots, and future holdings.

Example transaction request:

```json
{
  "symbol": "ORCL",
  "brokerageAccountId": "...",
  "transactionType": "BUY",
  "quantity": 5,
  "price": 142.50,
  "transactionDate": "2026-08-23"
}
```

The user should never need to know internal database identifiers such as `tradableAssetId`.

---

## 2. Current Backend Structure

The backend is currently divided into two main services:

```text
backend/
├── user-service/
└── portfolio-service/
```

### User Service

Responsible for user-related information.

### Portfolio Service

Contains the investment and portfolio management logic.

The main components currently implemented are:

```text
Portfolio
BrokerageAccount
TradableAsset
PortfolioTransaction
PositionLot
```

These components represent the current foundation of the portfolio management system.

---

## 3. Current Domain Flow

The current application flow can be represented as:

```text
User
  ↓
Portfolio
  ↓
BrokerageAccount

TradableAsset
  ↓

PortfolioTransaction
  ↓
PositionLot
```

The future flow will continue with:

```text
PositionLot
   ↓
Holding
   ↓
Dashboard
```

---

## 4. Portfolio

`Portfolio` represents an investment portfolio owned by a user.

Conceptually:

```text
User
  ↓
Portfolio
```

A portfolio is associated with the user through a `userId`.

The Portfolio Service stores the user's UUID instead of creating a direct database relationship with the User entity from the User Service.

This keeps the two microservices independent.

Example:

```text
User
id = U001
```

```text
Portfolio
id = P001
userId = U001
```

---

## 5. BrokerageAccount

`BrokerageAccount` represents the brokerage platform or brokerage account through which the user performs transactions.

Examples:

```text
XTB
Interactive Brokers
Trading 212
Revolut
```

A portfolio can contain multiple brokerage accounts.

Example:

```text
Main Portfolio
├── XTB
├── Interactive Brokers
└── Trading 212
```

The brokerage account contains information such as:

```text
brokerName
accountName
currency
active
```

It does not store calculated values such as portfolio profit, current value, or invested capital.

Those values will later be handled by the portfolio aggregation and dashboard logic.

---

## 6. TradableAsset

`TradableAsset` represents information about a financial instrument.

Example:

```text
symbol       = ORCL
displayName  = Oracle Corporation
exchangeCode = NYSE
currency     = USD
assetType    = STOCK
sector       = Technology
industry     = Software
```

A `TradableAsset` does not represent a user's position.

It only represents the financial product itself.

The same asset can therefore be referenced by multiple transactions.

---

## 7. PortfolioTransaction

`PortfolioTransaction` represents the historical record of an investment operation.

Examples:

```text
BUY
ORCL
XTB
5 shares
$142.50
23.08.2026
```

or:

```text
SELL
ORCL
XTB
3 shares
$160.00
10.09.2026
```

A transaction currently stores:

```text
portfolioId
tradableAssetId
brokerageAccountId
transactionType
quantity
price
transactionDate
```

The supported transaction types are currently:

```text
BUY
SELL
```

---

## 8. Why Transactions Must Remain Historical

A transaction represents something that already happened.

For example:

```text
10 January
BUY 5 ORCL @ $100
```

If the user later sells:

```text
23 August
SELL 3 ORCL @ $150
```

the original BUY transaction must remain:

```text
BUY 5 ORCL @ $100
```

It must not be modified to:

```text
BUY 2 ORCL @ $100
```

The transaction history must remain unchanged.

The remaining open quantity is managed separately through `PositionLot`.

---

## 9. PositionLot

`PositionLot` represents the portion of a BUY transaction that is still part of the user's current open position.

The current rule is:

```text
1 BUY transaction = 1 PositionLot
```

Example:

```text
BUY 5 ORCL @ $100
```

creates:

```text
PortfolioTransaction

BUY 5 ORCL @ $100
```

and automatically:

```text
PositionLot

quantity = 5
status = OPEN
```

This allows the application to separate historical information from the current state of the position.

---

## 10. Automatic BUY Flow

The BUY flow is already implemented.

When a BUY transaction is created, the backend automatically creates the corresponding position lot.

Current flow:

```text
POST Transaction
      ↓
PortfolioTransactionService
      ↓
Resolve / validate TradableAsset
      ↓
Validate BrokerageAccount
      ↓
Create PortfolioTransaction
      ↓
Transaction Type == BUY
      ↓
PositionLotService.createLot()
      ↓
Create OPEN PositionLot
```

Example:

```text
BUY 5 ORCL @ $142.50 through XTB
```

Database result:

```text
PortfolioTransaction

type     = BUY
asset    = ORCL
broker   = XTB
quantity = 5
price    = 142.50
```

and:

```text
PositionLot

quantity = 5
status   = OPEN
```

This functionality is already working.

---

## 11. PositionLot Structure

The current simplified `PositionLot` contains:

```text
id
portfolioId
tradableAssetId
brokerageAccountId
buyTransactionId
quantity
status
```

The available statuses are:

```text
OPEN
CLOSED
```

### buyTransactionId

`buyTransactionId` references the BUY transaction that originally created the lot.

Example:

```text
PortfolioTransaction
id = T001
type = BUY
quantity = 5
```

creates:

```text
PositionLot
id = L001
buyTransactionId = T001
quantity = 5
status = OPEN
```

---

## 12. Why PositionLot Contains Only One Quantity

The lot stores only:

```text
quantity
```

instead of:

```text
originalQuantity
remainingQuantity
```

The original quantity already exists in the BUY transaction.

Example:

```text
PortfolioTransaction
quantity = 5
```

The `PositionLot.quantity` represents only the quantity that is still open.

Initially:

```text
Transaction quantity = 5
Lot quantity         = 5
```

After a partial SELL:

```text
Transaction quantity = 5
Lot quantity         = 2
```

The original BUY remains unchanged.

---

## 13. Planned SELL Behavior

The full SELL-to-lot logic is not implemented yet.

The intended behavior is the following.

Initial BUY:

```text
BUY 5 ORCL
```

Position lot:

```text
quantity = 5
status = OPEN
```

User sells:

```text
SELL 3 ORCL
```

A new SELL transaction will be stored:

```text
SELL 3 ORCL
```

and the existing lot will become:

```text
quantity = 2
status = OPEN
```

If the remaining two shares are later sold:

```text
SELL 2 ORCL
```

the lot becomes:

```text
quantity = 0
status = CLOSED
```

The lot will not be physically deleted from the database.

It will remain available for historical tracking and future calculations.

---

## 14. Transaction vs PositionLot

These two components have different responsibilities.

```text
PortfolioTransaction
= what happened historically
```

```text
PositionLot
= what remains open from a BUY
```

Example transaction history:

```text
BUY  5 ORCL @ $100
SELL 3 ORCL @ $150
```

Current open lot:

```text
ORCL
2 shares remaining
```

This separation is important for future portfolio and dashboard calculations.

---

## 15. Future Holding Component

`Holding` has not yet been implemented.

Its responsibility will be to represent the consolidated current position for one financial asset.

Example open lots:

```text
ORCL

XTB
2 shares

Interactive Brokers
10 shares

Trading 212
7 shares
```

The future Holding will represent:

```text
ORCL
quantity = 19
averagePrice = ...
```

The intended relationship is:

```text
PortfolioTransaction
        ↓
PositionLot
        ↓
Holding
        ↓
Dashboard
```

The responsibilities will therefore be:

```text
PortfolioTransaction
→ complete historical activity

PositionLot
→ state of each individual BUY

Holding
→ consolidated position

Dashboard
→ information displayed to the user
```

---

## 16. Future Dashboard Behavior

The dashboard should display one main row for one financial asset, regardless of how many BUY transactions or brokerage accounts exist.

Example:

```text
ORCL

XTB
BUY 5

Interactive Brokers
BUY 10

Trading 212
BUY 7
```

The dashboard should display:

```text
ORCL | 22 shares | Average Price | Current Price | P/L
```

It should not display three separate ORCL positions.

When the user expands the ORCL position, the application should display only the BUY lots that still contribute to the current open position.

Example:

```text
ORCL — Open Position

XTB
BUY | 2 shares remaining

Interactive Brokers
BUY | 10 shares remaining

Trading 212
BUY | 7 shares remaining
```

---

## 17. Transaction History

Transaction history is separate from open positions.

Example:

```text
Transaction History

BUY  | ORCL | XTB  | 5 | $100
SELL | ORCL | XTB  | 3 | $150
BUY  | ORCL | IBKR | 10 | $110
```

Even if a lot becomes `CLOSED`, the BUY and SELL transactions remain available in transaction history.

---

## 18. Real MVP Transaction Request

During early backend development, transactions were created using `tradableAssetId`.

The API has now been adapted toward the real frontend flow.

Instead of:

```json
{
  "tradableAssetId": "...",
  "brokerageAccountId": "...",
  "transactionType": "BUY",
  "quantity": 5,
  "price": 142.50,
  "transactionDate": "2026-08-23"
}
```

the frontend can send:

```json
{
  "symbol": "ORCL",
  "brokerageAccountId": "...",
  "transactionType": "BUY",
  "quantity": 5,
  "price": 142.50,
  "transactionDate": "2026-08-23"
}
```

The backend resolves:

```text
ORCL
 ↓
TradableAsset
 ↓
tradableAssetId
```

The user therefore works with financial information instead of database identifiers.

---

## 19. Frontend Responsibility

The user will see:

```text
Symbol:   ORCL
Broker:   XTB
Type:     BUY
Quantity: 5
Price:    142.50
Date:     23.08.2026
```

The frontend will internally know:

```text
portfolioId
brokerageAccountId
```

For example, the user selects:

```text
XTB
```

but the frontend sends:

```text
brokerageAccountId = UUID
```

The user never needs to interact with that UUID.

---

## 20. Current Backend Status

The following components are currently implemented:

* [x] Portfolio
* [x] Portfolio basic CRUD / flow
* [x] TradableAsset
* [x] TradableAsset DTOs
* [x] TradableAsset mapper
* [x] TradableAsset CRUD
* [x] BrokerageAccount
* [x] BrokerageAccount DTOs
* [x] BrokerageAccount mapper
* [x] BrokerageAccount CRUD
* [x] BrokerageAccount exception handling
* [x] PortfolioTransaction
* [x] PortfolioTransaction DTOs
* [x] PortfolioTransaction mapper
* [x] PortfolioTransaction CRUD
* [x] BUY / SELL transaction types
* [x] TradableAsset validation
* [x] BrokerageAccount validation
* [x] Symbol-based TradableAsset resolution
* [x] PositionLot entity
* [x] PositionLot repository
* [x] PositionLot service foundation
* [x] Automatic BUY → PositionLot creation

Not yet implemented:

* [ ] SELL → PositionLot consumption
* [ ] insufficient quantity validation
* [ ] FIFO lot matching
* [ ] additional lot matching strategies
* [ ] Holding
* [ ] consolidated position calculation
* [ ] average price calculation
* [ ] dashboard API
* [ ] market-price integration

---

## 21. Frontend Status

The frontend project has already been initialized.

Current stack:

```text
Next.js
React
TypeScript
Tailwind CSS
```

The frontend will communicate with the Spring Boot backend through REST APIs.

Current frontend status:

* [x] Next.js project initialized
* [x] React installed
* [x] TypeScript configured
* [x] Tailwind CSS configured
* [ ] shadcn/ui
* [ ] Lucide React
* [ ] application layout
* [ ] Portfolio interface
* [ ] Add Transaction form
* [ ] API integration
* [ ] Transaction History
* [ ] Open Positions
* [ ] Dashboard

---

## 22. Next Development Steps

### Frontend

The next development stage will focus on creating a simple functional interface that replaces manual Postman testing.

Planned order:

```text
1. Configure frontend structure
2. Configure shadcn/ui and Lucide
3. Create Play & Hold base layout
4. Create Portfolio page
5. Load Brokerage Accounts
6. Create Add Transaction form
7. Connect frontend to Spring Boot
8. Display Transaction History
```

### Backend

After the first frontend flow is functional, backend development will continue with:

```text
1. SELL validation
2. PositionLot consumption
3. Full and partial lot closing
4. FIFO lot matching
5. Holding
6. Consolidated positions
7. Average price calculation
8. Dashboard API
```

---

## 23. Current Application Flow

The current and planned architecture can be summarized as:

```text
User
 ↓
Portfolio
 ├── BrokerageAccount
 │
 └── PortfolioTransaction
          ↑
     TradableAsset
          │
          ↓ BUY
     PositionLot
          │
          ↓
     Holding          [NEXT]
          │
          ↓
     Dashboard        [PLANNED]
```

At the current development milestone, the backend foundation required for creating portfolio transactions and automatically generating BUY position lots is functional.

The next major stage is the frontend transaction flow, followed by SELL processing, Holding aggregation, and the final portfolio dashboard.
