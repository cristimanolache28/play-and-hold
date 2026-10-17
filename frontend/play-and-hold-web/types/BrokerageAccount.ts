export type SellMode =
  | "LOT_BASED"
  | "POSITION_BASED";

export type LotAllocationMethod =
  | "FIFO"
  | "LIFO"
  | null;

export type BrokerageAccount = {
  id: string;
  portfolioId: string;
  brokerName: string;
  accountName: string;
  currency: string;
  sellMode: SellMode;
  lotAllocationMethod: LotAllocationMethod;
  active: boolean;
};
