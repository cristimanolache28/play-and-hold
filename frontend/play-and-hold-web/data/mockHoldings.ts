import type { Holding } from "@/types/Holding";

export const mockHoldings: Holding[] = [
  {
    symbol: "ORCL",
    companyName: "Oracle",
    quantity: 7,
    averagePrice: 144.52,
    marketPrice: 160,
  },
  {
    symbol: "SOFI",
    companyName: "SoFi Technologies",
    quantity: 60,
    averagePrice: 17.95,
    marketPrice: 18.6,
  },
  {
    symbol: "TSLA",
    companyName: "Tesla",
    quantity: 0.5,
    averagePrice: 373.69,
    marketPrice: 350,
  },
];