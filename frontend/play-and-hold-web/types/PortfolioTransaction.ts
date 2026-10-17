export type TransactionType = "BUY" | "SELL";

export interface CreatePortfolioTransactionRequest {
  symbol: string;
  brokerageAccountId: string;
  transactionType: TransactionType;
  quantity: number;
  price: number;
  fees?: number | null;
  executedAt?: string | null;
  notes?: string | null;
}

export interface PortfolioTransaction {
  id: string;
  portfolioId: string;
  tradableAssetId: string;
  brokerageAccountId: string;
  transactionType: TransactionType;
  quantity: number;
  price: number;
  fees: number | null;
  executedAt: string | null;
  notes: string | null;
}