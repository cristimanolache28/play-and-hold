"use client";

import { useEffect, useState } from "react";

import { getBrokerageAccounts } from "@/lib/api/portfolioApi";
import type { BrokerageAccount } from "@/types/BrokerageAccount";

const TEST_PORTFOLIO_ID = "7aea14ad-b298-4fad-8a0f-3131dfa29db2";

export default function TransactionsPage() {
  const [brokerageAccounts, setBrokerageAccounts] = useState<
    BrokerageAccount[]
  >([]);

  const [loading, setLoading] = useState(true);

  const [error, setError] = useState<string | null>(null);

  useEffect(() => {
    async function loadBrokerageAccounts() {
      try {
        const accounts = await getBrokerageAccounts(TEST_PORTFOLIO_ID);

        setBrokerageAccounts(accounts);
      } catch (error) {
        console.error("Failed to load brokerage accounts:", error);

        setError("Could not load brokerage accounts");
      }
    }

    loadBrokerageAccounts();
  }, []);

  return (
    <div className="p-8">
      <h1 className="text-3xl font-bold text-white">Transactions</h1>

      <p className="mt-2 text-gray-400">Manage your investment transactions.</p>

      <div className="mt-8">
        <h2 className="text-xl font-semibold text-white">Brokerage Accounts</h2>

        {loading && (
          <p className="mt-4 text-gray-400">Loading brokerage accounts...</p>
        )}

        {error && <p className="mt-4 text-red-500">{error}</p>}

        {!loading &&
          !error &&
          brokerageAccounts.map((account) => (
            <div
              key={account.id}
              className="mt-4 rounded-xl border border-gray-800 p-4"
            >
              <p className="font-medium text-white">{account.brokerName}</p>

              <p className="text-sm text-gray-400">{account.accountName}</p>

              <p className="mt-2 text-sm text-gray-500">
                {account.sellMode}
                {account.lotAllocationMethod &&
                  ` · ${account.lotAllocationMethod}`}
              </p>
            </div>
          ))}
      </div>
    </div>
  );
}
