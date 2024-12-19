package usersdatastore

import dtos.TransactionDTO
import dtos.WalletDTO
import exceptions.InsuficientFundsException
import grails.gorm.transactions.Transactional
import users.TransactionType
import users.User
import users.Wallet
import users.WalletTransactions

import java.time.LocalDateTime

@Transactional
class WalletService {

    WalletDTO getUserWallet(User user) {
        Wallet wallet = Wallet.findByUser(user)

        return new WalletDTO(wallet)
    }

    WalletDTO addTransaction(User user, TransactionDTO transactionsDTO) {
        Wallet wallet = Wallet.findByUser(user)

        if (transactionsDTO.transactionType == TransactionType.WITHDRAWAL) {
            if (transactionsDTO.amount > wallet.amount) {
                throw new InsuficientFundsException('Insuficiente funds')
            }

            WalletTransactions walletTransactions = new WalletTransactions(
                    amount: transactionsDTO.amount,
                    transactionType: transactionsDTO.transactionType,
                    timeStamp: LocalDateTime.now(),
                    wallet: wallet
            )

            wallet.amount -= transactionsDTO.amount

            wallet.save(flush:true)
            walletTransactions.save(flush:true)
        } else {
            WalletTransactions walletTransactions = new WalletTransactions(
                    amount: transactionsDTO.amount,
                    transactionType: transactionsDTO.transactionType,
                    timeStamp: LocalDateTime.now(),
                    wallet: wallet
            )

            wallet.amount += transactionsDTO.amount
            wallet.save(flush:true)
            walletTransactions.save(flush:true)
        }

        return new WalletDTO(wallet)
    }
}
