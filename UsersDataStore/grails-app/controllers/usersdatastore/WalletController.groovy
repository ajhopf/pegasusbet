package usersdatastore

import dtos.TransactionDTO
import dtos.WalletDTO
import dtos.WalletTransactionsDTO
import exceptions.InsuficientFundsException
import grails.plugin.springsecurity.annotation.Secured
import org.springframework.security.core.context.SecurityContextHolder
import users.User


@Secured(['isAuthenticated()'])
class WalletController {
    WalletService walletService

	static responseFormats = ['json', 'xml']

    def getWalletInfo() {
        try {
            String username = SecurityContextHolder.context.authentication.name
            User currentUser = User.findByUsername(username)

            if (!currentUser) {
                render(status: 401, contentType: "application/json") {
                    message "Unauthorized"
                }
                return
            }

            WalletDTO walletDTO = walletService.getUserWallet(currentUser)

            render(status: 200, contentType: "application/json") {
                "wallet" walletDTO
            }
        } catch (Exception e) {
            render(status: 500, contentType: "application/json") {
                message "Não foi possivel completar a chamada"
                cause e.getCause()
            }
        }
    }

    def getWalletTransactions() {
        try {
            String username = SecurityContextHolder.context.authentication.name
            User currentUser = User.findByUsername(username)

            if (!currentUser) {
                render(status: 401, contentType: "application/json") {
                    message "Unauthorized"
                }
                return
            }

            List<WalletTransactionsDTO> walletTransactions = walletService.getUserWalletTransactions(currentUser)

            render(status: 200, contentType: "application/json") {
                "transactions" walletTransactions
            }
        } catch (Exception e) {
            render(status: 500, contentType: "application/json") {
                message "Não foi possivel completar a chamada"
                cause e.getCause()
            }
        }
    }

    def addTransaction(TransactionDTO transactionDTO) {
        try {
            String username = SecurityContextHolder.context.authentication.name
            User currentUser = User.findByUsername(username)

            if (!currentUser) {
                render(status: 401, contentType: "application/json") {
                    message "Unauthorized"
                }
                return
            }
            WalletDTO walletDTO = walletService.addTransaction(currentUser, transactionDTO)

            render(status: 200, contentType: "application/json") {
                "wallet" walletDTO
            }
        } catch (InsuficientFundsException e) {
            render(status: 400, contentType: "application/json") {
                message e.getMessage()
            }
        } catch (Exception e) {
            render(status: 500, contentType: "application/json") {
                message "Não foi possivel completar a chamada"
                cause e.getCause()
            }
        }
    }

}
