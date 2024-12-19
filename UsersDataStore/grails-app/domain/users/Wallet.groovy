package users

class Wallet {
    BigDecimal amount
    User user

    static belongsTo = [user: User]
    static hasMany = [walletTransactions: WalletTransactions]

    static constraints = {
        walletTransactions nullable: true
    }
}
