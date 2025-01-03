package usersdatastore

import dao.WalletDAO
import dtos.TransactionDTO
import enums.TransactionType
import exceptions.InsuficientFundsException
import grails.testing.gorm.DataTest
import grails.testing.services.ServiceUnitTest
import org.apache.kafka.common.errors.ResourceNotFoundException
import spock.lang.Specification
import users.User
import users.Wallet

class WalletServiceTest extends Specification implements ServiceUnitTest<WalletService> , DataTest  {
    WalletDAO walletDAO = Mock()

    User user = null

    def setup() {
        service.walletDAO = walletDAO

        this.user = new User(id: 1,
            wallet: new Wallet()
        )

    }

    def "addTransaction lança ResourceNotFoundException quando wallet para user não for encontrada"() {
        given: "Uma transactionDTO"
        TransactionDTO transactionDTO = new TransactionDTO()

        when:
        service.addTransaction(user, transactionDTO)

        then:
        thrown(ResourceNotFoundException)
    }

    def "addTransaction lança InsuficientFundsException quando user não tiver recursos suficientes"() {
        given: "Uma transactionDTO do tipo WITHDRAWAL com valor 100"
        TransactionDTO transactionDTO = new TransactionDTO(
                transactionType: TransactionType.WITHDRAWAL,
                amount: 100
        )
        and: "Uma carteira com 99 de amount"
        Wallet wallet = new Wallet(amount: 99)
        walletDAO.findByUser(_) >> wallet

        when:
        service.addTransaction(user, transactionDTO)

        then:
        thrown(InsuficientFundsException)
    }

    def "addTransaction reduz o amount da wallet do user com uma transaction do tipo PLACE_BET"() {
        given: "Uma transactionDTO do tipo PLACE_BET com valor 100"
        TransactionDTO transactionDTO = new TransactionDTO(
                transactionType: TransactionType.PLACE_BET,
                amount: 100
        )
        and: "Uma carteira com recurso suficiente"
        Wallet wallet = new Wallet(amount: 100)
        wallet.id = 1
        walletDAO.findByUser(_) >> wallet

        Wallet returnedWallet = new Wallet(amount:0)
        returnedWallet.id = 1
        walletDAO.saveWallet(_ as Wallet) >> returnedWallet

        when:
        service.addTransaction(user, transactionDTO)

        then: "WalletTransaction é invocada com wallet com amount reduzida"
        1 * walletDAO.saveWalletTransactions({wallet.amount == 0 })
    }

    def "addTransaction adiciona o amount com transaction do tipo DEPOSIT"() {
        given: "Uma transactionDTO do tipo DEPOSIT com valor 100"
        TransactionDTO transactionDTO = new TransactionDTO(
                transactionType: TransactionType.DEPOSIT,
                amount: 100
        )
        and: "Uma carteira sem recursos iniciais"
        Wallet wallet = new Wallet(amount: 0)
        wallet.id = 1
        walletDAO.findByUser(_) >> wallet

        Wallet returnedWallet = new Wallet(amount:100)
        returnedWallet.id = 1
        walletDAO.saveWallet(_ as Wallet) >> returnedWallet

        when:
        service.addTransaction(user, transactionDTO)

        then: "WalletTransaction é invocada com wallet com amount adicionada"
        1 * walletDAO.saveWalletTransactions({wallet.amount == 100 })
    }

}
