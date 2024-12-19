package users

import java.time.LocalDateTime

class WalletTransactions {
    Wallet wallet
    TransactionType transactionType
    BigDecimal amount
    LocalDateTime timeStamp

    static belongsTo = [wallet: Wallet]

    static constraints = {
        transactionType nullable: false, blank: false
        amount nullable: false
    }
}
