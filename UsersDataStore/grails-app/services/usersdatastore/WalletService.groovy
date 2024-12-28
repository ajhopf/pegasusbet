package usersdatastore

import dtos.TransactionDTO
import dtos.WalletDTO
import exceptions.InsuficientFundsException
import grails.gorm.transactions.Transactional
import enums.TransactionType
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

        println 'no wallet add transaction'

        println transactionsDTO

        if (!wallet) {
            throw new IllegalStateException('Wallet not found for user: ' + user.id)
        }

        if ((transactionsDTO.transactionType == TransactionType.WITHDRAWAL || transactionsDTO.transactionType == TransactionType.PLACE_BET) && transactionsDTO.amount > wallet.amount) {
            throw new InsuficientFundsException('Recursos Insuficientes')
        }

        if (transactionsDTO.transactionType in [TransactionType.WITHDRAWAL, TransactionType.PLACE_BET]) {
            wallet.amount -= transactionsDTO.amount
        } else {
            wallet.amount += transactionsDTO.amount
        }

        wallet = wallet.save(flush:true)

        WalletTransactions walletTransactions = new WalletTransactions(
                amount: transactionsDTO.amount,
                transactionType: transactionsDTO.transactionType,
                timeStamp: LocalDateTime.now(),
                wallet: wallet
        )

        walletTransactions.save(flush:true)

        return new WalletDTO(wallet)
    }
}
