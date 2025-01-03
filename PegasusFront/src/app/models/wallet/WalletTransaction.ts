export interface WalletTransaction {
  transactionType: 'DEPOSIT' | 'WITHDRAWAL' | 'PLACE_BET' | 'BET_WIN'
  amount: number
  timeStamp: string
}
