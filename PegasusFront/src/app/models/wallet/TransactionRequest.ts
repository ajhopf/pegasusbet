export interface TransactionRequest {
  amount: number;
  transactionType: 'DEPOSIT' | 'WITHDRAWAL'
}
