"use client";

import type { BrokerageAccount } from "@/types/BrokerageAccount";
import { useEffect, useState } from "react";
import type { SubmitEvent } from "react";
import type { TradableAsset } from "@/types/TradableAsset";

import {
  createPortfolioTransaction,
  getBrokerageAccounts,
  searchTradableAssets,
} from "@/lib/api/portfolioApi";

const TEST_PORTFOLIO_ID = "7aea14ad-b298-4fad-8a0f-3131dfa29db2";

export default function TransactionsPage() {
  const [brokerageAccounts, setBrokerageAccounts] = useState<
    BrokerageAccount[]
  >([]);

  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);
  const [selectedBrokerageAccountId, setSelectedBrokerageAccountId] =
    useState("");
  const [symbol, setSymbol] = useState("");
  const [assetResults, setAssetResults] = useState<TradableAsset[]>([]);
  const [assetSearchLoading, setAssetSearchLoading] = useState(false);
  const [assetSearchError, setAssetSearchError] = useState<string | null>(null);
  const [quantity, setQuantity] = useState("");
  const [price, setPrice] = useState("");
  const [executedAt, setExecutedAt] = useState("");
  const [submitting, setSubmitting] = useState(false);
  const [submitError, setSubmitError] = useState<string | null>(null);
  const [successMessage, setSuccessMessage] = useState<string | null>(null);

  useEffect(() => {
    async function loadBrokerageAccounts() {
      try {
        const accounts = await getBrokerageAccounts(TEST_PORTFOLIO_ID);

        setBrokerageAccounts(accounts);
      } catch (error) {
        console.error("Failed to load brokerage accounts:", error);

        setError("Could not load brokerage accounts");
      } finally {
        setLoading(false);
      }
    }

    loadBrokerageAccounts();
  }, []);

  async function handleSubmit(event: SubmitEvent<HTMLFormElement>) {
    event.preventDefault();

    setSubmitting(true);
    setSubmitError(null);
    setSuccessMessage(null);

    try {
      await createPortfolioTransaction(TEST_PORTFOLIO_ID, {
        symbol: symbol.trim().toUpperCase(),
        brokerageAccountId: selectedBrokerageAccountId,
        transactionType: "BUY",
        quantity: Number(quantity),
        price: Number(price),
        fees: 0,
        executedAt: executedAt ? new Date(executedAt).toISOString() : null,
        notes: null,
      });

      setSuccessMessage(
        `${symbol.trim().toUpperCase()} transaction created successfully.`,
      );

      setSymbol("");
      setQuantity("");
      setPrice("");
      setExecutedAt("");
    } catch (error) {
      console.error("Failed to create transaction:", error);

      setSubmitError("Could not create transaction.");
    } finally {
      setSubmitting(false);
    }
  }
  useEffect(() => {
    const normalizedQuery = symbol.trim();

    if (normalizedQuery.length < 2) {
      setAssetResults([]);
      setAssetSearchError(null);
      return;
    }

    const timeoutId = setTimeout(async () => {
      setAssetSearchLoading(true);
      setAssetSearchError(null);

      try {
        const results = await searchTradableAssets(normalizedQuery);

        setAssetResults(results);
      } catch (error) {
        console.error("Failed to search tradable assets:", error);

        setAssetSearchError("Could not search assets.");

        setAssetResults([]);
      } finally {
        setAssetSearchLoading(false);
      }
    }, 300);

    return () => {
      clearTimeout(timeoutId);
    };
  }, [symbol]);

  const activeAccounts = brokerageAccounts.filter(
    (account) => account.active,
  ).length;

  const transactionValue = Number(quantity || 0) * Number(price || 0);

  return (
    <main className="min-h-screen bg-[#05070b] px-6 py-8 text-white lg:px-10">
      {/* HEADER */}
      <section className="mx-auto max-w-7xl">
        <div className="relative overflow-hidden rounded-3xl border border-white/10 bg-gradient-to-br from-white/[0.08] via-white/[0.03] to-transparent p-8">
          <div className="absolute -right-24 -top-32 h-80 w-80 rounded-full bg-violet-500/20 blur-3xl" />
          <div className="absolute -bottom-40 left-1/3 h-80 w-80 rounded-full bg-cyan-500/10 blur-3xl" />

          <div className="relative">
            <div className="flex flex-col justify-between gap-6 lg:flex-row lg:items-end">
              <div>
                <div className="mb-3 inline-flex items-center rounded-full border border-emerald-400/20 bg-emerald-400/10 px-3 py-1 text-xs font-medium text-emerald-300">
                  Portfolio execution
                </div>

                <h1 className="text-4xl font-semibold tracking-tight">
                  Transactions
                </h1>

                <p className="mt-3 max-w-xl text-sm leading-6 text-zinc-400">
                  Add investments, manage brokerage activity and keep your
                  portfolio history centralized.
                </p>
              </div>

              <div className="flex gap-3">
                <div className="rounded-2xl border border-white/10 bg-black/20 px-5 py-3 backdrop-blur">
                  <p className="text-xs text-zinc-500">Accounts</p>

                  <p className="mt-1 text-xl font-semibold">
                    {brokerageAccounts.length}
                  </p>
                </div>

                <div className="rounded-2xl border border-white/10 bg-black/20 px-5 py-3 backdrop-blur">
                  <p className="text-xs text-zinc-500">Active</p>

                  <p className="mt-1 text-xl font-semibold text-emerald-400">
                    {activeAccounts}
                  </p>
                </div>
              </div>
            </div>
          </div>
        </div>

        {/* MAIN GRID */}
        <div className="mt-8 grid gap-8 xl:grid-cols-[1.5fr_0.8fr]">
          {/* TRANSACTION CARD */}
          <section className="relative overflow-hidden rounded-3xl border border-white/10 bg-[#0a0d13]">
            <div className="absolute left-0 top-0 h-px w-full bg-gradient-to-r from-transparent via-violet-400/70 to-transparent" />

            <div className="border-b border-white/10 p-6 lg:p-8">
              <div className="flex flex-col justify-between gap-5 sm:flex-row sm:items-center">
                <div>
                  <p className="text-xs font-medium uppercase tracking-[0.2em] text-zinc-500">
                    New order
                  </p>

                  <h2 className="mt-2 text-2xl font-semibold">
                    Add Transaction
                  </h2>
                </div>

                <div className="inline-flex w-fit rounded-xl border border-white/10 bg-black/40 p-1">
                  <button
                    type="button"
                    className="rounded-lg bg-emerald-400 px-5 py-2 text-sm font-semibold text-black shadow-lg shadow-emerald-500/10"
                  >
                    BUY
                  </button>

                  <button
                    type="button"
                    disabled
                    className="cursor-not-allowed rounded-lg px-5 py-2 text-sm font-medium text-zinc-600"
                  >
                    SELL
                  </button>
                </div>
              </div>
            </div>

            <form onSubmit={handleSubmit} className="p-6 lg:p-8">
              <div className="grid gap-6 md:grid-cols-2">
                {/* ASSET */}
                <div>
                  <label className="mb-2 block text-xs font-medium uppercase tracking-wider text-zinc-500">
                    Asset
                  </label>

                  <div className="relative">
                    <input
                      type="text"
                      value={symbol}
                      onChange={(event) => setSymbol(event.target.value)}
                      placeholder="Search ticker..."
                      required
                      className="h-14 w-full rounded-2xl border border-white/10 bg-white/[0.03] px-4 text-base font-medium uppercase text-white outline-none transition placeholder:normal-case placeholder:text-zinc-600 focus:border-violet-400/60 focus:bg-white/[0.05]"
                    />
                  </div>

                  <p className="mt-2 text-xs text-zinc-600">
                    Asset search will replace the current ticker input.
                  </p>
                </div>

                {/* BROKER */}
                <div>
                  <label className="mb-2 block text-xs font-medium uppercase tracking-wider text-zinc-500">
                    Brokerage Account
                  </label>

                  <select
                    value={selectedBrokerageAccountId}
                    onChange={(event) =>
                      setSelectedBrokerageAccountId(event.target.value)
                    }
                    required
                    className="h-14 w-full rounded-2xl border border-white/10 bg-[#0d1118] px-4 text-sm text-white outline-none transition focus:border-violet-400/60"
                  >
                    <option value="">Select brokerage account</option>

                    {brokerageAccounts.map((account) => (
                      <option key={account.id} value={account.id}>
                        {account.brokerName} — {account.accountName}
                      </option>
                    ))}
                  </select>
                </div>

                {/* QUANTITY */}
                <div>
                  <label className="mb-2 block text-xs font-medium uppercase tracking-wider text-zinc-500">
                    Quantity
                  </label>

                  <input
                    type="number"
                    step="any"
                    min="0"
                    value={quantity}
                    onChange={(event) => setQuantity(event.target.value)}
                    placeholder="0"
                    required
                    className="h-14 w-full rounded-2xl border border-white/10 bg-white/[0.03] px-4 text-lg font-medium text-white outline-none transition placeholder:text-zinc-700 focus:border-cyan-400/60 focus:bg-white/[0.05]"
                  />
                </div>

                {/* PRICE */}
                <div>
                  <label className="mb-2 block text-xs font-medium uppercase tracking-wider text-zinc-500">
                    Price per share
                  </label>

                  <div className="relative">
                    <span className="absolute left-4 top-1/2 -translate-y-1/2 text-zinc-500">
                      $
                    </span>

                    <input
                      type="number"
                      step="0.01"
                      min="0"
                      value={price}
                      onChange={(event) => setPrice(event.target.value)}
                      placeholder="0.00"
                      required
                      className="h-14 w-full rounded-2xl border border-white/10 bg-white/[0.03] pl-8 pr-4 text-lg font-medium text-white outline-none transition placeholder:text-zinc-700 focus:border-cyan-400/60 focus:bg-white/[0.05]"
                    />
                  </div>
                </div>

                {/* DATE */}
                <div className="md:col-span-2">
                  <label className="mb-2 block text-xs font-medium uppercase tracking-wider text-zinc-500">
                    Execution Date
                  </label>

                  <input
                    type="datetime-local"
                    value={executedAt}
                    onChange={(event) => setExecutedAt(event.target.value)}
                    className="h-14 w-full rounded-2xl border border-white/10 bg-white/[0.03] px-4 text-sm text-zinc-300 outline-none transition focus:border-violet-400/60 md:w-1/2"
                  />

                  <p className="mt-2 text-xs text-zinc-600">
                    Optional. Leave empty to use the current execution time.
                  </p>
                </div>
              </div>

              {/* ORDER SUMMARY */}
              <div className="mt-8 rounded-2xl border border-white/10 bg-gradient-to-r from-violet-500/[0.07] to-cyan-500/[0.05] p-5">
                <div className="flex flex-col justify-between gap-4 sm:flex-row sm:items-center">
                  <div>
                    <p className="text-xs uppercase tracking-wider text-zinc-500">
                      Estimated transaction value
                    </p>

                    <p className="mt-2 text-2xl font-semibold tracking-tight">
                      $
                      {transactionValue.toLocaleString("en-US", {
                        minimumFractionDigits: 2,
                        maximumFractionDigits: 2,
                      })}
                    </p>
                  </div>

                  <div className="text-left sm:text-right">
                    <p className="text-xs text-zinc-500">Order type</p>

                    <div className="mt-1 inline-flex items-center gap-2 text-sm font-semibold text-emerald-400">
                      <span className="h-2 w-2 rounded-full bg-emerald-400" />
                      BUY
                    </div>
                  </div>
                </div>
              </div>

              {/* FEEDBACK */}
              {submitError && (
                <div className="mt-6 rounded-2xl border border-red-500/20 bg-red-500/10 px-4 py-3 text-sm text-red-300">
                  {submitError}
                </div>
              )}

              {successMessage && (
                <div className="mt-6 rounded-2xl border border-emerald-500/20 bg-emerald-500/10 px-4 py-3 text-sm text-emerald-300">
                  {successMessage}
                </div>
              )}

              {/* SUBMIT */}
              <div className="mt-8 flex justify-end">
                <button
                  type="submit"
                  disabled={submitting}
                  className="group relative overflow-hidden rounded-2xl bg-gradient-to-r from-violet-500 via-fuchsia-500 to-cyan-400 px-8 py-3.5 text-sm font-semibold text-white shadow-lg shadow-violet-500/20 transition hover:scale-[1.02] disabled:cursor-not-allowed disabled:opacity-50 disabled:hover:scale-100"
                >
                  <span className="relative z-10">
                    {submitting
                      ? "Creating transaction..."
                      : "Add BUY Transaction"}
                  </span>
                </button>
              </div>
            </form>
          </section>

          {/* BROKERAGE SIDEBAR */}
          <aside className="rounded-3xl border border-white/10 bg-[#0a0d13] p-6">
            <div className="flex items-center justify-between">
              <div>
                <p className="text-xs font-medium uppercase tracking-[0.2em] text-zinc-500">
                  Connected
                </p>

                <h2 className="mt-2 text-xl font-semibold">
                  Brokerage Accounts
                </h2>
              </div>

              <div className="h-3 w-3 rounded-full bg-emerald-400 shadow-[0_0_16px_rgba(52,211,153,0.8)]" />
            </div>

            {loading && (
              <div className="mt-6 rounded-2xl border border-white/10 bg-white/[0.03] p-4 text-sm text-zinc-500">
                Loading brokerage accounts...
              </div>
            )}

            {error && (
              <div className="mt-6 rounded-2xl border border-red-500/20 bg-red-500/10 p-4 text-sm text-red-300">
                {error}
              </div>
            )}

            <div className="mt-6 space-y-3">
              {!loading &&
                !error &&
                brokerageAccounts.map((account) => {
                  const isSelected = account.id === selectedBrokerageAccountId;

                  return (
                    <button
                      type="button"
                      key={account.id}
                      onClick={() => setSelectedBrokerageAccountId(account.id)}
                      className={`w-full rounded-2xl border p-4 text-left transition ${
                        isSelected
                          ? "border-violet-400/50 bg-violet-500/10"
                          : "border-white/10 bg-white/[0.02] hover:border-white/20 hover:bg-white/[0.04]"
                      }`}
                    >
                      <div className="flex items-start justify-between gap-3">
                        <div>
                          <p className="font-medium text-white">
                            {account.brokerName}
                          </p>

                          <p className="mt-1 text-xs text-zinc-500">
                            {account.accountName}
                          </p>
                        </div>

                        <span
                          className={`mt-1 h-2 w-2 rounded-full ${
                            account.active ? "bg-emerald-400" : "bg-zinc-700"
                          }`}
                        />
                      </div>

                      <div className="mt-4 flex items-center gap-2">
                        <span className="rounded-lg bg-white/[0.05] px-2 py-1 text-[11px] text-zinc-400">
                          {account.currency}
                        </span>

                        <span className="rounded-lg bg-white/[0.05] px-2 py-1 text-[11px] text-zinc-400">
                          {account.sellMode}
                        </span>

                        {account.lotAllocationMethod && (
                          <span className="rounded-lg bg-white/[0.05] px-2 py-1 text-[11px] text-zinc-400">
                            {account.lotAllocationMethod}
                          </span>
                        )}
                      </div>
                    </button>
                  );
                })}
            </div>
          </aside>
        </div>

        {/* TRANSACTION HISTORY PLACEHOLDER */}
        <section className="mt-8 rounded-3xl border border-white/10 bg-[#0a0d13] p-6 lg:p-8">
          <div className="flex flex-col justify-between gap-4 sm:flex-row sm:items-center">
            <div>
              <p className="text-xs font-medium uppercase tracking-[0.2em] text-zinc-500">
                Activity
              </p>

              <h2 className="mt-2 text-xl font-semibold">
                Transaction History
              </h2>
            </div>

            <div className="rounded-xl border border-white/10 bg-white/[0.03] px-4 py-2 text-xs text-zinc-500">
              History integration next
            </div>
          </div>

          <div className="mt-8 flex min-h-40 items-center justify-center rounded-2xl border border-dashed border-white/10 bg-black/20">
            <div className="text-center">
              <p className="font-medium text-zinc-300">
                Your transactions will appear here
              </p>

              <p className="mt-2 text-sm text-zinc-600">
                Search, filters and complete transaction history will be added
                next.
              </p>
            </div>
          </div>
        </section>
      </section>
    </main>
  );
}
