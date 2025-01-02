package usersdatastore

import dao.WalletDAO
import dtos.TransactionDTO
import dtos.WalletDTO
import exceptions.InsuficientFundsException
import grails.gorm.transactions.Transactional
import enums.TransactionType
import org.apache.kafka.common.errors.ResourceNotFoundException
import users.User
import users.Wallet
import users.WalletTransactions

import java.time.LocalDateTime

@Transactional
class WalletService {
    WalletDAO walletDAO

    WalletDTO getUserWallet(User user) {
        Wallet wallet = walletDAO.findByUser(user)

        return new WalletDTO(wallet)
    }

    WalletDTO addTransaction(User user, TransactionDTO transactionsDTO) {
        Wallet wallet = walletDAO.findByUser(user)

        if (!wallet) {
            throw new ResourceNotFoundException('Wallet not found for user: ' + user.id)
        }

        if ((transactionsDTO.transactionType == TransactionType.WITHDRAWAL || transactionsDTO.transactionType == TransactionType.PLACE_BET) && transactionsDTO.amount > wallet.amount) {
            throw new InsuficientFundsException('Recursos Insuficientes')
        }

        if (transactionsDTO.transactionType in [TransactionType.WITHDRAWAL, TransactionType.PLACE_BET]) {
            wallet.amount -= transactionsDTO.amount
        } else {
            wallet.amount += transactionsDTO.amount
        }

        wallet = walletDAO.saveWallet(wallet)

        WalletTransactions walletTransactions = new WalletTransactions(
                amount: transactionsDTO.amount,
                transactionType: transactionsDTO.transactionType,
                timeStamp: LocalDateTime.now(),
                wallet: wallet
        )

        walletDAO.saveWalletTransactions(walletTransactions)

        return new WalletDTO(wallet)
    }
}
