export type AssetType =
  | "STOCK"
  | "ETF"
  | "CRYPTO"
  | "BOND"
  | "COMMODITY";

export type TradableAsset = {
  id: string;
  symbol: string;
  displayName: string;
  exchangeCode: string;
  currency: string;
  assetType: AssetType;
  sector: string | null;
  industry: string | null;
  active: boolean;
};