package dtos

import enums.TransactionType
import users.WalletTransactions

class WalletTransactionsDTO {
    TransactionType transactionType
    BigDecimal amount
    String timeStamp

    WalletTransactionsDTO(WalletTransactions walletTransaction) {
        this.transactionType = walletTransaction.transactionType
        this.amount = walletTransaction.amount
        this.timeStamp = walletTransaction.timeStamp.toString()
    }
}
