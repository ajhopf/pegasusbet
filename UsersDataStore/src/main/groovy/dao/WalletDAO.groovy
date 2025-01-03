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

    List<WalletTransactions> getUserWalletTransactions(User user) {
        Wallet wallet = Wallet.findByUser(user)

        return WalletTransactions.findAllByWallet(wallet)
    }

    WalletTransactions saveWalletTransactions(WalletTransactions walletTransactions) {
        return walletTransactions.save(flush:true)
    }
}
