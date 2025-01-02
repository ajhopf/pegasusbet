package dao

import users.User
import users.Wallet
import users.WalletTransactions

class WalletDAO {
    Wallet findByUser(User user) {
        return Wallet.findByUser(user)
    }

    Wallet saveWallet(Wallet wallet) {
        wallet.save(flush: true)
    }

    WalletTransactions saveWalletTransactions(WalletTransactions walletTransactions) {
        return walletTransactions.save(flush:true)
    }
}
