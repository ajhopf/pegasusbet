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

        BigDecimal currentAmount = wallet.amount

        BigDecimal newAmount = transactionsDTO.transactionType in [TransactionType.WITHDRAWAL, TransactionType.PLACE_BET]
                ? currentAmount - transactionsDTO.amount
                : currentAmount + transactionsDTO.amount

        wallet.amount = newAmount

        wallet.save(flush:true)

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
