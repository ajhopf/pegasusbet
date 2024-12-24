package usersdatastore

import dtos.TransactionDTO
import dtos.WalletDTO
import exceptions.InsuficientFundsException
import grails.plugin.springsecurity.annotation.Secured
import users.User


@Secured(['isAuthenticated()'])
class WalletController {
    WalletService walletService

	static responseFormats = ['json', 'xml']

    def getWalletInfo(String username) {
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

    }

    def addTransaction(String username, TransactionDTO transactionDTO) {
        User currentUser = User.findByUsername(username)

        if (!currentUser) {
            render(status: 401, contentType: "application/json") {
                message "Unauthorized"
            }
            return
        }

        try {
            WalletDTO walletDTO = walletService.addTransaction(currentUser, transactionDTO)

            render(status: 200, contentType: "application/json") {
                "wallet" walletDTO
            }
        } catch (InsuficientFundsException e) {
            render(status: 400, contentType: "application/json") {
                message e.getMessage()
            }
        }
    }

}
