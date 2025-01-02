package usersdatastore

import dao.BetDAO
import dao.WalletDAO
import dtos.bet.CreateBetDTO
import enums.BetStatus
import enums.BetType
import exceptions.InsuficientFundsException
import grails.testing.gorm.DataTest
import grails.testing.services.ServiceUnitTest
import spock.lang.Specification
import users.Bet
import users.User
import users.Wallet

class BetsServiceTest extends Specification implements ServiceUnitTest<BetsService>, DataTest{
    BetDAO betDAO = Mock()
    WalletDAO walletDAO = Mock()
    WalletService walletService = Mock()

    User user = null
    Wallet wallet = null
    CreateBetDTO createBetDTO = null

    def setup() {
        service.betDAO = betDAO
        service.walletDAO = walletDAO
        service.walletService = walletService

        this.wallet = new Wallet(amount: 0)
        this.user = new User(wallet: this.wallet)
        this.createBetDTO = new CreateBetDTO(amount: 100)
    }

    def "createBet lança InsuficienteFundsException quando usuario nao possui recursos"() {
        given:
        walletDAO.findByUser(_) >> wallet

        when:
        service.createBet(user, createBetDTO)

        then:
        thrown(InsuficientFundsException)
    }


    def "determineBetStatus retorna status correto de acordo com o betType e resultPosition"() {
        given:
        Bet bet = new Bet(betType: betType)

        when:
        BetStatus status = service.determineBetStatus(bet, resultPosition)

        then:
        status == expectedStatus

        where:
        betType          | resultPosition || expectedStatus
        BetType.WIN      | 1              || BetStatus.WIN
        BetType.WIN      | 2              || BetStatus.LOSS
        BetType.PLACE    | 1              || BetStatus.WIN
        BetType.PLACE    | 2              || BetStatus.WIN
        BetType.PLACE    | 3              || BetStatus.LOSS
        BetType.SHOW     | 1              || BetStatus.WIN
        BetType.SHOW     | 2              || BetStatus.WIN
        BetType.SHOW     | 3              || BetStatus.WIN
        BetType.SHOW     | 4              || BetStatus.LOSS
    }

    def "processBetWin invoca o método addTransaction do walletService com o amount correto de acordo com o tipo de Bet"() {
        given:
        Bet bet = new Bet(
                betType: betType,
                amount: 10
        )

        when:
        service.processBetWin(bet, multiplier)

        then:
        1 * walletService.addTransaction({}, {it.amount == expectedAmount})

        where:
        betType          | multiplier || expectedAmount
        BetType.WIN      | 2.0        || 20.0
        BetType.WIN      | 2.5        || 25.0
        BetType.PLACE    | 2.0        || 17.0
        BetType.PLACE    | 3.0        || 24.0
        BetType.SHOW     | 2.0        || 15.0
        BetType.SHOW     | 3.0        || 20.0
    }
}
