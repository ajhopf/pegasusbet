package usersdatastore

import dtos.TransactionDTO
import dtos.WalletDTO
import exceptions.InsuficientFundsException
import grails.plugin.springsecurity.SpringSecurityService
import grails.plugin.springsecurity.annotation.Secured
import users.User


@Secured(['isAuthenticated()'])
class WalletController {
    WalletService walletService
    SpringSecurityService springSecurityService

	static responseFormats = ['json', 'xml']

    def getWalletInfo() {
        User currentUser = springSecurityService.currentUser as User

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

    def addTransaction(TransactionDTO transactionDTO) {
        User currentUser = springSecurityService.currentUser as User

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
