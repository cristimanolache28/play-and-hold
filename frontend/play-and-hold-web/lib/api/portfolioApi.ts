import type { BrokerageAccount } from "@/types/BrokerageAccount";
import type {CreatePortfolioTransactionRequest,PortfolioTransaction,} from "@/types/PortfolioTransaction";

const API_URL = process.env.NEXT_PUBLIC_PORTFOLIO_API_URL;

if (!API_URL) {
  throw new Error("NEXT_PUBLIC_PORTFOLIO_API_URL is not configured");
}

export async function getBrokerageAccounts(
  portfolioId: string,
): Promise<BrokerageAccount[]> {
  console.log("Fetching brokerage accounts...");
  const response = await fetch(
    `${API_URL}/portfolios/${portfolioId}/brokerage-accounts`,
  );

  if (!response.ok) {
    throw new Error("Failed to load brokerage accounts");
  }

  return response.json();
}

export async function createPortfolioTransaction(
  portfolioId: string,
  transaction: CreatePortfolioTransactionRequest,
): Promise<PortfolioTransaction> {
  const response = await fetch(
    `${API_URL}/portfolios/${portfolioId}/transactions`,
    {
      method: "POST",
      headers: {"Content-Type": "application/json", },
      body: JSON.stringify(transaction),
    },
  );

  if (!response.ok) {
    throw new Error("Failed to create transaction");
  }

  return response.json();
}
